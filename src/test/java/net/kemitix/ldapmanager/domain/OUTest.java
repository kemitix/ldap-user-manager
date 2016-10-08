package net.kemitix.ldapmanager.domain;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OU}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class OUTest {

    @Test
    public void shouldBuilder() throws Exception {
        //given
        val ouToString = OU.builder()
                           .dn(LdapNameBuilder.newInstance("ou=the-name")
                                              .build())
                           .ou("name")
                           .toString();
        //then
        assertThat(ouToString).containsPattern("[( ]dn=ou=the-name[,)]")
                              .containsPattern("[( ]ou=name[,)]");
    }

    @Test
    public void canInstantiateNoArgs() {
        assertThat(new OU().getDn()).isNull();
    }

    @Test
    public void shouldGetCn() throws Exception {
        //given
        val user = OU.builder()
                     .dn(LdapNameBuilder.newInstance("ou=name")
                                        .build())
                     .ou("name")
                     .build();
        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(user.getDn()
                              .toString())
              .isEqualTo("ou=name");
        softly.assertThat(user.getOu())
              .isEqualTo("name");
        softly.assertAll();
    }

    @Test
    public void shouldGetOuForName() {
        //given
        val user = OU.builder()
                     .dn(LdapNameBuilder.newInstance("ou=name")
                                        .build())
                     .ou("name")
                     .build();
        //then
        assertThat(user.name()).isEqualTo("name");
    }
}
