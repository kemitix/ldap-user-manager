package net.kemitix.ldapmanager.domain;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

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
                               .dn(LdapNameUtil.parse("cn=the-name"))
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
                       .dn(LdapNameUtil.parse("cn=name"))
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
                       .dn(LdapNameUtil.parse("cn=name"))
                       .cn("name")
                       .sn("surname")
                       .build();
        //then
        assertThat(user.name()).isEqualTo("name");
    }

    @Test
    public void shouldHaveFeatureRename() {
        //given
        val user = User.builder()
                       .build();
        //then
        assertThat(user.hasFeature(Features.RENAME)).isTrue();
    }

    @Test
    public void shouldHaveFeaturePassword() {
        //given
        val user = User.builder()
                       .build();
        //then
        assertThat(user.hasFeature(Features.PASSWORD)).isTrue();
    }

    @Test
    public void shouldGetFeatureSet() {
        //given
        val user = User.builder()
                       .build();
        //then
        assertThat(user.getFeatureSet()).containsExactly(Features.PASSWORD, Features.RENAME);
    }
}
