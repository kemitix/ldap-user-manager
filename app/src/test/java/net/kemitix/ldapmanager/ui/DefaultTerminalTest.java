package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.SimpleTerminalResizeListener;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link DefaultTerminal}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultTerminalTest {

    @InjectMocks
    private DefaultTerminal terminal;

    @Mock
    private TerminalFactory terminalFactory;

    @Mock
    private Terminal delegate;

    @Mock
    private TextGraphics textGraphics;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInitAndThenDelegate() throws Exception {
        //given
        given(terminalFactory.createTerminal()).willReturn(delegate);
        //when
        terminal.init();
        //then
        terminal.clearScreen();
        then(delegate).should()
                      .clearScreen();

        terminal.setCursorPosition(1, 2);
        then(delegate).should()
                      .setCursorPosition(1, 2);

        terminal.enquireTerminal(1, TimeUnit.MICROSECONDS);
        then(delegate).should()
                      .enquireTerminal(1, TimeUnit.MICROSECONDS);

        terminal.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
        then(delegate).should()
                      .setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);

        terminal.setCursorVisible(true);
        then(delegate).should()
                      .setCursorVisible(true);
        terminal.setCursorVisible(false);
        then(delegate).should()
                      .setCursorVisible(false);

        terminal.putCharacter('a');
        then(delegate).should()
                      .putCharacter('a');

        terminal.enableSGR(SGR.BLINK);
        then(delegate).should()
                      .enableSGR(SGR.BLINK);

        terminal.disableSGR(SGR.BLINK);
        then(delegate).should()
                      .disableSGR(SGR.BLINK);

        terminal.setForegroundColor(TextColor.ANSI.BLUE);
        then(delegate).should()
                      .setForegroundColor(TextColor.ANSI.BLUE);

        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
        then(delegate).should()
                      .setBackgroundColor(TextColor.ANSI.BLACK);

        final SimpleTerminalResizeListener resizeListener = new SimpleTerminalResizeListener(TerminalSize.ONE);

        terminal.addResizeListener(resizeListener);
        then(delegate).should()
                      .addResizeListener(resizeListener);

        terminal.removeResizeListener(resizeListener);
        then(delegate).should()
                      .removeResizeListener(resizeListener);

        terminal.enterPrivateMode();
        then(delegate).should()
                      .enterPrivateMode();

        terminal.exitPrivateMode();
        then(delegate).should()
                      .exitPrivateMode();

        given(delegate.getCursorPosition()).willReturn(TerminalPosition.TOP_LEFT_CORNER);
        assertThat(terminal.getCursorPosition()).isSameAs(TerminalPosition.TOP_LEFT_CORNER);

        given(delegate.newTextGraphics()).willReturn(textGraphics);
        assertThat(terminal.newTextGraphics()).isSameAs(textGraphics);

        terminal.resetColorAndSGR();
        then(delegate).should()
                      .resetColorAndSGR();

        given(delegate.getTerminalSize()).willReturn(TerminalSize.ONE);
        assertThat(terminal.getTerminalSize()).isSameAs(TerminalSize.ONE);

        terminal.bell();
        then(delegate).should()
                      .bell();

        terminal.flush();
        then(delegate).should()
                      .flush();

        final KeyStroke keyStroke = new KeyStroke(KeyType.Enter);

        given(delegate.pollInput()).willReturn(keyStroke);
        assertThat(terminal.pollInput()).isSameAs(keyStroke);

        given(delegate.readInput()).willReturn(keyStroke);
        assertThat(terminal.readInput()).isSameAs(keyStroke);
    }
}
