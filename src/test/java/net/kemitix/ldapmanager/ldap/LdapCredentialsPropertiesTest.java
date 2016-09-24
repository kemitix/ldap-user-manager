package net.kemitix.ldapmanager.ldap;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapCredentialsProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LdapCredentialsPropertiesTest {

    private static final String LDAP_SERVER = "ldap server";

    private static final String BASE_OU = "base ou";

    private static final String USER_DN = "user dn";

    private static final String PASS = "pass";

    private LdapCredentialsProperties properties;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Before
    public void setUp() throws Exception {
        properties = new LdapCredentialsProperties();
        properties.setUrls(new String[]{LDAP_SERVER});
        properties.setBase(BASE_OU);
        properties.setUserDn(USER_DN);
        properties.setPassword(PASS);
    }

    @Test
    public void shouldLogProperties() throws Exception {
        //given
        //when
        properties.logProperties();
        //then
        final String output = outputCapture.toString();
        assertThat(output).contains("url: ldap server");
        assertThat(output).contains("base: base ou");
        assertThat(output).contains("userDn: user dn");
        assertThat(output).contains("password: ****");
    }

    @Test
    public void getters() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(properties.getUrls())
              .containsOnly(LDAP_SERVER);
        softly.assertThat(properties.getBase())
              .isEqualTo(BASE_OU);
        softly.assertThat(properties.getUserDn())
              .isEqualTo(USER_DN);
        softly.assertThat(properties.getPassword())
              .isEqualTo(PASS);
        softly.assertAll();
    }
}
