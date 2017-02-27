package net.kemitix.ldapmanager.events;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ContainerExpiredEvent}.
 */
public class ContainerExpiredEventTest {

    @Test
    public void containing() throws Exception {
        //given
        val dn = LdapNameUtil.parse("cn=bob,ou=users");
        //when
        val result = ContainerExpiredEvent.containing(dn);
        //then
        assertThat(result.getContainers()).containsExactly(LdapNameUtil.parse("ou=users"));
    }

    @Test
    public void of() throws Exception {
        //given
        val dn = LdapNameUtil.parse("ou=users");
        //when
        val result = ContainerExpiredEvent.of(dn);
        //then
        assertThat(result.getContainers()).containsExactly(dn);
    }
}
