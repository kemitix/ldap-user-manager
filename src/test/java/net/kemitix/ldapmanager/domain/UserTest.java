package net.kemitix.ldapmanager.domain;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
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
                               .dn(LdapNameBuilder.newInstance("cn=the-name")
                                                  .build())
                               .cn("name")
                               .sn("surname")
                               .toString();
        //then
        assertThat(userToString).containsPattern("[( ]dn=cn=the-name[,)]")
                                .containsPattern("[( ]cn=name[,)]")
                                .containsPattern("[( ]sn=surname[,)]");
    }

    @Test
    public void canInstantiateNoArgs() {
        assertThat(new User().getDn()).isNull();
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        val user = User.builder()
                       .dn(LdapNameBuilder.newInstance("cn=name")
                                          .build())
                       .cn("name")
                       .sn("surname")
                       .build();
        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(user.getDn()
                              .toString())
              .isEqualTo("cn=name");
        softly.assertThat(user.getCn())
              .isEqualTo("name");
        softly.assertThat(user.getSn())
              .isEqualTo("surname");
        softly.assertAll();
    }

    @Test
    public void shouldGetCnForName() {
        //given
        val user = User.builder()
                       .dn(LdapNameBuilder.newInstance("cn=name")
                                          .build())
                       .cn("name")
                       .sn("surname")
                       .build();
        //then
        assertThat(user.name())
              .isEqualTo("name");
    }
}
