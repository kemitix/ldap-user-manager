package net.kemitix.ldapmanager;

import net.kemitix.ldapmanager.ldap.LdapAttribute;
import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

/**
 * Tests for {@link Messages}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MessagesTest {

    @Test
    public void shouldBeUtilityClass() throws Exception {
        UtilityClassTestUtil.assertUtilityClassWellDefined(LdapAttribute.class);
    }
}
