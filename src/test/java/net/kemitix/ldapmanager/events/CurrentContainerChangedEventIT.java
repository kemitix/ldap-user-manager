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

package net.kemitix.ldapmanager.events;

import com.googlecode.lanterna.gui2.Label;
import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainerMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.ldap.LdapName;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Tests for the {@link CurrentContainerChanged} event.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class CurrentContainerChangedEventIT {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private CurrentContainer currentContainer;

    @Autowired
    private Label currentOuLabel;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private LdapEntityContainerMap containerMap;

    private LdapName newContainer;

    @Before
    public void setUp() {
        newContainer = LdapNameBuilder.newInstance("ou=new")
                                      .build();
        currentContainer.setDn(newContainer);
    }

    @Test
    public void shouldUpdateCurrentUiLabel() {
        //when
        eventPublisher.publishEvent(CurrentContainerChangedEvent.of(newContainer));
        //then
        assertThat(currentOuLabel.getText()).isEqualTo(newContainer.toString());
    }

    @Test
    public void shouldLoadContainerObjects() throws InterruptedException {
        //given
        val ou = OU.builder()
                   .build();
        val user = User.builder()
                       .build();
        given(ldapTemplate.find(any(LdapQuery.class), eq(OU.class))).willReturn(Collections.singletonList(ou));
        given(ldapTemplate.find(any(LdapQuery.class), eq(User.class))).willReturn(Collections.singletonList(user));
        //when
        eventPublisher.publishEvent(CurrentContainerChangedEvent.of(newContainer));
        //then
        assertThat(containerMap.findOrDefault(newContainer, LdapEntityContainer.empty())
                               .getContents()).containsExactlyInAnyOrder(ou, user);
    }
}
