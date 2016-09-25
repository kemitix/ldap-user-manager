package net.kemitix.ldapmanager.ldap;

import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

/**
 * Tests for {@link ObjectClass}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class ObjectClassTest {

    @Test
    public void shouldBeUtilityClass() throws Exception {
        UtilityClassTestUtil.assertUtilityClassWellDefined(ObjectClass.class);
    }
}
