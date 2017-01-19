package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

/**
 * Tests for {@link AbstractListenableListBox}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class AbstractListenableListBoxTest {

    private AbstractListenableListBoxTest.TestAbstractListenableListBox abstractListenableListBox;

    @Before
    public void setUp() throws Exception {
        abstractListenableListBox = spy(new AbstractListenableListBoxTest.TestAbstractListenableListBox());
    }

    @Test
    public void shouldHandleKeyStrokeHandled() throws Exception {
        //given
        final KeyStroke keyStroke = new KeyStroke(KeyType.Escape);
        final Interactable.Result handled = Interactable.Result.HANDLED;
        given(abstractListenableListBox.onHandleKeyStroke(keyStroke)).willReturn(handled);
        //when
        final Interactable.Result result = abstractListenableListBox.handleKeyStroke(keyStroke);
        //then
        assertThat(result).isEqualTo(handled);
    }

    @Test
    public void shouldHandleKeyStrokeUnhandled() throws Exception {
        //given
        final KeyStroke keyStroke = new KeyStroke(KeyType.Escape);
        final Interactable.Result unhandled = Interactable.Result.UNHANDLED;
        given(abstractListenableListBox.onHandleKeyStroke(keyStroke)).willReturn(unhandled);
        //when
        final Interactable.Result result = abstractListenableListBox.handleKeyStroke(keyStroke);
        //then
        assertThat(result).isEqualTo(unhandled);
    }

    @Test
    public void shouldAfterEnterFocus() throws Exception {
        abstractListenableListBox.afterEnterFocus(Interactable.FocusChangeDirection.DOWN, abstractListenableListBox);
        //doesn't throw an exception - not much else I can test here
    }

    @Test
    public void shouldAddItem() throws Exception {
        abstractListenableListBox.onSelectionChange(1, 2);
        //doesn't throw an exception - not much else I can test here
    }

    private static class TestAbstractListenableListBox
            extends AbstractListenableListBox<String, AbstractListenableListBoxTest.TestAbstractListenableListBox> {

        TestAbstractListenableListBox() {
        }
    }
}
