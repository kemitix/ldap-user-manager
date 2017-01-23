package net.kemitix.ldapmanager.ldap;

import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

/**
 * Tests for {@link LdapAttribute}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LdapAttributeTest {

    @Test
    public void shouldBeUtilityClass() throws Exception {
        UtilityClassTestUtil.assertUtilityClassWellDefined(LdapAttribute.class);
    }
}
