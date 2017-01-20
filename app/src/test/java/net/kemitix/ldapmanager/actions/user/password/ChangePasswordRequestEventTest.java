package net.kemitix.ldapmanager.actions.user.password;

import lombok.val;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ChangePasswordRequestEvent}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ChangePasswordRequestEventTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldCreateAndGetNavigationItemBackOut() throws Exception {
        //when
        val dn = LdapNameUtil.parse("cn=user");
        val event = ChangePasswordRequestEvent.of(dn);
        //then
        assertThat(event.getDn()).isSameAs(dn);
    }

    @Test
    public void shouldThrowNPEWhenNavigationItemIsNull() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("dn");
        //when
        ChangePasswordRequestEvent.of(null);
    }
}
