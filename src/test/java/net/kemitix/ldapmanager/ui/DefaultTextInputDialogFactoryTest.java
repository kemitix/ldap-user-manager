package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DefaultTextInputDialogFactory}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultTextInputDialogFactoryTest {

    private DefaultTextInputDialogFactory dialogFactory;

    @Mock
    private WindowBasedTextGUI gui;

    @Mock
    private TextInputDialog textInputDialog;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dialogFactory = new DefaultTextInputDialogFactory(gui);
    }

    @Test
    public void shouldGetDialog() {
        //when
        final TextInputDialog dialog = dialogFactory.create("", null);
        //then
        assertThat(dialog).isNotNull();
    }

    @Test
    public void shouldGetDialogWithValidator() {
        //when
        final TextInputDialog dialog = dialogFactory.create("", content -> null);
        //then
        assertThat(dialog).isNotNull();
    }

    @Test
    public void shouldGetInput() throws Exception {
        //given
        val expected = "expected";
        given(textInputDialog.showDialog(gui)).willReturn(expected);
        //when
        final String result = dialogFactory.getInput(textInputDialog);
        //then
        assertThat(result).isEqualTo(expected);
    }
}
