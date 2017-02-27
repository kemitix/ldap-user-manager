package net.kemitix.ldapmanager.domain;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OU}.
 */
public class OUTest {

    @Test
    public void builder() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val ou = "ou";
        //when
        val result = OU.builder()
                       .dn(dn)
                       .ou(ou)
                       .build();
        //then
        assertThat(result).isInstanceOf(OU.class);
        assertThat(result.getDn()).isSameAs(dn);
        assertThat(result.getOu()).isSameAs(ou);
    }

    @Test
    public void of() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val ou = "ou";
        //when
        val result = OU.of(dn, ou);
        //then
        assertThat(result).isInstanceOf(OU.class);
        assertThat(result.getDn()).isSameAs(dn);
        assertThat(result.getOu()).isSameAs(ou);
    }

    @Test
    public void nonRenameable() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val ou = "ou";
        //when
        val result = OU.nonRenameable(dn, ou);
        //then
        assertThat(result).isInstanceOf(OU.class);
        assertThat(result.getDn()).isSameAs(dn);
        assertThat(result.getOu()).isSameAs(ou);
    }

    @Test
    public void name() throws Exception {
        //given
        val dn = LdapNameUtil.empty();
        val ou = "ou";
        val subject = OU.builder()
                        .dn(dn)
                        .ou(ou)
                        .build();
        //when
        val result = subject.name();
        //then
        assertThat(result).isEqualTo(ou);
    }
}
