package net.kemitix.ldapmanager.events;

import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

/**
 * Tests for {@link ApplicationExitEvent}
 */
public class ApplicationExitEventTest {

    @Test
    public void privateConstructor() throws ReflectiveOperationException {
        UtilityClassTestUtil.assertUtilityClassWellDefined(ApplicationExitEvent.class);
    }
}
