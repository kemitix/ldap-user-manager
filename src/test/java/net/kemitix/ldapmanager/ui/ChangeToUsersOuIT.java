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

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.ldap.AbstractLdapConnectionIntegrationTest;
import net.kemitix.ldapmanager.ldap.LdapOptions;
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
public class ChangeToUsersOuIT extends AbstractLdapConnectionIntegrationTest {

    @Autowired
    private CurrentOuLabel currentOuLabel;

    @Autowired
    private NavigationPanel navigationPanel;

    private ActionListBox actionListBox;

    @Autowired
    private LdapOptions ldapOptions;

    @Before
    public void setUp() {
        actionListBox = navigationPanel.getActionListBox();
    }

    @Test
    public void navigateToUsers() {
        //given
        currentOuLabel.setText("unchanged");
        navigationPanel.onCurrentContainerChangedEventUpdateNavigationItems();
        assertThat(actionListBox.getItemCount()).as("users is listed and ready")
                                                .isGreaterThan(0);
        actionListBox.getItems()
                     .forEach(item -> System.out.println("item = " + item));
        assertThat(actionListBox.getItemAt(0)
                                .toString()).isEqualTo("users");
        //when
        actionListBox.setSelectedIndex(0);
        actionListBox.handleKeyStroke(new KeyStroke(KeyType.Enter));
        //then
        assertThat(currentOuLabel.getText()).isEqualTo("ou=users," + ldapOptions.getBase());
    }
}
