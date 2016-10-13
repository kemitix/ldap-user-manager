/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.ldapmanager.ui;

import net.kemitix.ldapmanager.ldap.AbstractLdapConnectionIntegrationTest;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.LdapOptions;
import net.kemitix.ldapmanager.navigation.NavigationItemActionListBox;
import net.kemitix.ldapmanager.state.CurrentContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for navigating to the container 'ou=users,dc=kemitix,dc=net'.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "ldap-connection-it"})
public class ChangeOuIT extends AbstractLdapConnectionIntegrationTest {

    private static final String OU_NAME = "test1";

    private static final String USER_NAME = "gary";

    @Autowired
    private CurrentContainer currentContainer;

    @Autowired
    private CurrentOuLabel currentOuLabel;

    @Autowired
    private NavigationItemActionListBox navigationItemActionListBox;

    @Autowired
    private LdapOptions ldapOptions;


    @Before
    public void setUp() {
        currentContainer.setDn(LdapNameUtil.empty());
        currentContainer.publishCurrentContainer();
        //        navigationItemActionListBox.updateNavigationItems();
        assertThat(currentOuLabel.getText()).isEqualTo(ldapOptions.getBase());
    }

    @Test
    public void navigateToTest1Ou() {
        //given
        assertThat(navigationItemActionListBox.findAndSelectItemByName(OU_NAME)).as("Select the OU")
                                                                                .isNotEmpty();
        //when
        navigationItemActionListBox.performSelectedItem();
        //then
        assertThat(currentOuLabel.getText()).as("Current OU label is updated")
                                            .isEqualTo(new StringBuilder().append("ou=")
                                                                          .append(OU_NAME)
                                                                          .append(',')
                                                                          .append(ldapOptions.getBase())
                                                                          .toString());
    }

    @Test
    public void selectingAUserDoesNotChangeOu() {
        //given
        assertThat(navigationItemActionListBox.findAndSelectItemByName(USER_NAME)).as("Select the user")
                                                                                  .isNotEmpty();
        //when
        navigationItemActionListBox.performSelectedItem();
        //then
        assertThat(currentOuLabel.getText()).as("Current OU label remains unchanged")
                                            .isEqualTo(ldapOptions.getBase());
    }

    @Test
    public void navigateToTest1OuThenToParent() {
        //given
        assertThat(navigationItemActionListBox.findAndSelectItemByName(OU_NAME)).as("Select the OU")
                                                                                .isNotEmpty();
        navigationItemActionListBox.performSelectedItem();
        assertThat(navigationItemActionListBox.findAndSelectItemByName("..")).as("Select the OU")
                                                                             .isNotEmpty();
        //when
        navigationItemActionListBox.performSelectedItem();
        //then
        assertThat(currentOuLabel.getText()).as("Current OU label is updated")
                                            .isEqualTo(ldapOptions.getBase());
    }
}
