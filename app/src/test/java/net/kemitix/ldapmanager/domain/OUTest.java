package net.kemitix.ldapmanager.domain;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

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
                           .dn(LdapNameUtil.parse("ou=the-name"))
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
                     .dn(LdapNameUtil.parse("ou=name"))
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
        val ou = OU.builder()
                   .dn(LdapNameUtil.parse("ou=name"))
                   .ou("name")
                   .build();
        //then
        assertThat(ou.name()).isEqualTo("name");
    }

    @Test
    public void shouldHaveFeatureRename() {
        //given
        val ou = OU.builder()
                   .build();
        //then
        assertThat(ou.hasFeature(Features.RENAME)).isTrue();
    }

    @Test
    public void shouldNotHaveFeaturePassword() {
        //given
        val ou = OU.builder()
                   .build();
        //then
        assertThat(ou.hasFeature(Features.PASSWORD)).isFalse();
    }

    @Test
    public void shouldGetFeatureSet() {
        //given
        val ou = OU.builder()
                   .build();
        //then
        assertThat(ou.getFeatureSet()).containsExactly(Features.RENAME);
    }
}
