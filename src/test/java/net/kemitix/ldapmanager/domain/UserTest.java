package net.kemitix.ldapmanager.domain;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link User}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class UserTest {

    @Test
    public void shouldBuilder() throws Exception {
        //given
        val userToString = User.builder()
                               .dn(LdapNameBuilder.newInstance("cn=the-name").build())
                               .cn("name")
                               .sn("surname")
                               .toString();
        //then
        Assertions.assertThat(userToString)
                  .containsPattern("[( ]dn=cn=the-name[,)]")
                  .containsPattern("[( ]cn=name[,)]")
                  .containsPattern("[( ]sn=surname[,)]")
        ;
    }

    @Test
    public void shouldGetCn() throws Exception {
        //given
        val user = User.builder()
                       .cn("name")
                       .build();
        //then
        assertThat(user.getCn()).isEqualTo("name");
    }
}
