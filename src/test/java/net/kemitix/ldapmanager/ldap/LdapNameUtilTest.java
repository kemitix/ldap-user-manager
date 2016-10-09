package net.kemitix.ldapmanager.ldap;

import lombok.val;
import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.ldap.InvalidNameException;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapNameUtil}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LdapNameUtilTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldBeUtilityClass() throws Exception {
        UtilityClassTestUtil.assertUtilityClassWellDefined(LdapAttribute.class);
    }

    @Test
    public void shouldGetParentWhenEmpty() throws Exception {
        //given
        Name dn = LdapNameBuilder.newInstance()
                                 .build();
        //when
        final Optional<Name> parentDn = LdapNameUtil.getParent(dn);
        //then
        assertThat(parentDn).isEmpty();
    }

    @Test
    public void shouldGetParentWhenNotEmpty() throws Exception {
        //given
        Name dn = LdapNameBuilder.newInstance("ou=dept")
                                 .add("ou", "users")
                                 .add("cn", "bob")
                                 .build();
        final Name expectedName = LdapNameBuilder.newInstance("ou=dept")
                                                 .add("ou", "users")
                                                 .build();
        assertThat(dn.toString()).isEqualTo("cn=bob,ou=users,ou=dept");
        assertThat(expectedName.toString()).isEqualTo("ou=users,ou=dept");
        //when
        final Optional<Name> parentDn = LdapNameUtil.getParent(dn);
        //then
        assertThat(parentDn).contains(expectedName);
    }

    @Test
    public void shouldGetEmptyDNWhenSingleRDNS() throws Exception {
        //given
        val dn = LdapNameBuilder.newInstance("cn=admin")
                                .build();
        val baseDn = LdapNameBuilder.newInstance()
                                    .build();
        //when
        val result = LdapNameUtil.getParent(dn);
        //then
        assertThat(result).contains(baseDn);
    }

    @Test
    public void shouldJoinTwoNames() throws Exception {
        //given
        val dn = LdapNameBuilder.newInstance("cn=bob,ou=users")
                                .build();
        val base = LdapNameBuilder.newInstance("dc=kemitix,dc=net")
                                  .build();
        //when
        val result = LdapNameUtil.join(dn, base);
        //then
        assertThat(result.toString()).isEqualTo("cn=bob,ou=users,dc=kemitix,dc=net");
    }

    @Test
    public void shouldParseValidName() throws Exception {
        //given
        val name = "cn=admin";
        //when
        val result = LdapNameUtil.parse(name);
        //then
        assertThat(result.toString()).isEqualTo(name);
    }

    @Test
    public void shouldParseInvalidValidName() throws Exception {
        //given
        val name = "admin";
        exception.expect(InvalidNameException.class);
        //when
        LdapNameUtil.parse(name);
    }
}
