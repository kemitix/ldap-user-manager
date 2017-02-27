package net.kemitix.ldapmanager.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.events.NavigationItemOuActionEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItemOuActionEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemOuActionEventTest {

    @Test
    public void shouldCreateEventAndGetOuBackOut() {
        //given
        val ou = OU.of(LdapNameUtil.empty(), "ou");
        //when
        val event = NavigationItemOuActionEvent.of(ou);
        //then
        assertThat(event.getOu()).isSameAs(ou);
    }
}
