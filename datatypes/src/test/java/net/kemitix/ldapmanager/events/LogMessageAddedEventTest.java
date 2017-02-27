package net.kemitix.ldapmanager.events;

import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

/**
 * Tests for {@link LogMessageAddedEvent}.
 */
public class LogMessageAddedEventTest {

    @Test
    public void privateConstructor() throws ReflectiveOperationException {
        UtilityClassTestUtil.assertUtilityClassWellDefined(LogMessageAddedEvent.class);
    }
}
