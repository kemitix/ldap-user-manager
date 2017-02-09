package net.kemitix.ldapmanager.handlers;

import com.googlecode.lanterna.input.KeyStroke;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link KeyStrokeHandler}.
 */
public class KeyStrokeHandlerTest {

    private KeyStrokeHandler handler;

    private String key;

    private String description;

    @Before
    public void setUp() throws Exception {
        key = "KEY";
        description = "DESCRIPTION";
        handler = new KeyStrokeHandler() {
            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public String getKey() {
                return key;
            }

            @Override
            public boolean canHandleKey(final KeyStroke key) {
                return false;
            }

            @Override
            public void handleInput(final KeyStroke key) {

            }
        };
    }

    @Test
    public void getPrompt() throws Exception {
        assertThat(handler.getPrompt()
                          .get()).contains(key)
                                 .contains(description);
    }
}
