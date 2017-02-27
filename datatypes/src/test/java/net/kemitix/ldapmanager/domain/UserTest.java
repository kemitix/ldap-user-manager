package net.kemitix.ldapmanager.domain;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link User}.
 */
public class UserTest {

    @Test
    public void builder() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val cn = "cn";
        val sn = "sn";
        //when
        val result = User.builder()
                         .dn(dn)
                         .cn(cn)
                         .sn(sn)
                         .build();
        //then
        assertThat(result).isInstanceOf(User.class);
    }

    @Test
    public void name() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val cn = "cn";
        val sn = "sn";
        val subject = User.builder()
                          .dn(dn)
                          .cn(cn)
                          .sn(sn)
                          .build();
        //when
        val result = subject.name();
        //then
        assertThat(result).isEqualTo(cn);
    }

    @Test
    public void create() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val cn = "cn";
        val sn = "sn";
        //when
        val result = User.create(dn, cn, sn);
        //then
        assertThat(result).isInstanceOf(User.class);
    }
}
