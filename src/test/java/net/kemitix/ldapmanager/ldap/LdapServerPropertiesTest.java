package net.kemitix.ldapmanager.ldap;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

/**
 * Tests for {@link LdapServerProperties}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LdapServerPropertiesTest {

    private static final String LDAP_SERVER = "ldap server";

    private static final String BASE_OU = "base ou";

    private static final String USER_DN = "user dn";

    private static final String PASS = "pass";

    private LdapServerProperties properties;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Before
    public void setUp() throws Exception {
        properties = new LdapServerProperties();
        properties.setUrls(new String[]{LDAP_SERVER});
        properties.setBase(BASE_OU);
        properties.setUserDn(USER_DN);
        properties.setPassword(PASS);
    }

    @Test
    public void getters() {
        val softly = new SoftAssertions();
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
