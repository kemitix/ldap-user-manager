package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultActionListDialogBuilderFactory}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultActionListDialogBuilderFactoryTest {

    @Test
    public void shouldCreate() throws Exception {
        assertThat(new DefaultActionListDialogBuilderFactory().create()).isInstanceOf(ActionListDialogBuilder.class);
    }
}
