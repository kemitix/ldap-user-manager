package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import lombok.val;
import net.kemitix.ldapmanager.LdapUserManagerException;
import net.kemitix.ldapmanager.events.ApplicationExitEvent;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

/**
 * Tests for {@link LanternaUi}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class LanternaUiTest {

    private LanternaUi ui;

    @Mock
    private BasicWindow mainWindow;

    @Mock
    private WindowBasedTextGUI gui;

    @Mock
    private LogMessages logMessages;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private MessageDialogBuilder messageDialogBuilder;

    @Mock
    private MessageDialog messageDialog;

    @Mock
    private StartupExceptionsCollector startupExceptionsCollector;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Mock
    private Screen screen;

    @Mock
    private TerminalSize terminalSize;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ui = new LanternaUi(logMessages, mainWindow, gui, eventPublisher, messageDialogBuilder,
                            startupExceptionsCollector
        );
        given(gui.getScreen()).willReturn(screen);
        given(screen.getTerminalSize()).willReturn(terminalSize);
        given(terminalSize.getColumns()).willReturn(80);
    }

    @Test
    public void run() throws IOException {
        //given
        given(startupExceptionsCollector.getExceptions()).willReturn(new ArrayList<>());
        //when
        ui.run();
        //then
        then(gui).should()
                 .addWindow(mainWindow);
        then(gui).should()
                 .waitForWindowToClose(mainWindow);
        then(logMessages).should(times(2))
                         .add(anyString());
    }

    @Test
    public void shouldCatchLdapUserManagerExceptionDuringMainLoopAndClose() throws IOException {
        //given
        val cause = new RuntimeException("the cause");
        val ldapUserManagerException = new LdapUserManagerException("the message", cause);
        willThrow(ldapUserManagerException).given(gui)
                                           .waitForWindowToClose(mainWindow);
        given(startupExceptionsCollector.getExceptions()).willReturn(new ArrayList<>());
        given(messageDialogBuilder.setTitle(anyString())).willReturn(messageDialogBuilder);
        given(messageDialogBuilder.setText(anyString())).willReturn(messageDialogBuilder);
        given(messageDialogBuilder.build()).willReturn(messageDialog);
        //when
        ui.run();
        //then
        then(messageDialogBuilder).should()
                                  .setTitle("Unhandled Error");
        then(messageDialogBuilder).should()
                                  .setText(stringCaptor.capture());
        assertThat(stringCaptor.getValue()).contains("the message")
                                           .contains("the cause");
        then(eventPublisher).should()
                            .publishEvent(any(ApplicationExitEvent.class));
    }

    @Test
    public void shouldCatchLdapUserManagerExceptionDuringStartupAndClose() throws IOException {
        //given
        val cause = new RuntimeException("the cause");
        val ldapUserManagerException = new LdapUserManagerException("the message", cause);
        given(startupExceptionsCollector.getExceptions()).willReturn(
                new ArrayList<>(Collections.singletonList(ldapUserManagerException)));
        given(messageDialogBuilder.setTitle(anyString())).willReturn(messageDialogBuilder);
        given(messageDialogBuilder.setText(anyString())).willReturn(messageDialogBuilder);
        given(messageDialogBuilder.build()).willReturn(messageDialog);
        //when
        ui.run();
        //then
        then(messageDialogBuilder).should()
                                  .setTitle("Unhandled Error");
        then(messageDialogBuilder).should()
                                  .setText(stringCaptor.capture());
        assertThat(stringCaptor.getValue()).contains("the message")
                                           .contains("the cause");
        then(eventPublisher).should()
                            .publishEvent(any(ApplicationExitEvent.class));
    }
}
