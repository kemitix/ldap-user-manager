package net.kemitix.ldapmanager.actions.user.create;

import com.googlecode.lanterna.gui2.BasePane;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.state.LogMessages;
import net.kemitix.ldapmanager.ui.FormContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link CreateUserFormPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CreateUserFormPanelTest {

    private CreateUserFormPanel panel;

    private FormContainer formContainer;

    @Mock
    private LogMessages logMessages;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private Name dn;

    @Mock
    private BasePane basePane;

    @Captor
    private ArgumentCaptor<CreateUserCommitEvent> eventArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        formContainer = new MyFormContainer(basePane);
        panel = new CreateUserFormPanel(formContainer, logMessages, applicationEventPublisher);
        panel.init();
    }

    @Test
    public void shouldOnCreateUserEvent() {
        //given
        panel.getCnTextBox()
             .setText("cn value");
        panel.getSnTextBox()
             .setText("sn value");
        //when
        panel.onCreateUserEvent(CreateUserEvent.create(dn));
        //then
        assertThat(panel.getCnTextBox()
                        .getText()).isEmpty();
        assertThat(panel.getSnTextBox()
                        .getText()).isEmpty();
    }

    @RequiredArgsConstructor
    private static class MyFormContainer extends Panel implements FormContainer {

        @Getter
        private final BasePane basePane;

        @Override
        public Panel replaceComponents(Component component) {
            return null;
        }
    }

    @Test
    public void shouldOnCreateButtonPressedDoNothingIfCnEmpty() {
        //given
        panel.getCnTextBox()
             .setText("");
        panel.getSnTextBox()
             .setText("sn value");
        //when
        panel.onCreateButtonPressed();
        //then
        then(logMessages).should(never())
                         .add(anyString());
        then(applicationEventPublisher).should(never())
                                       .publishEvent(any(CreateUserCommitEvent.class));
    }

    @Test
    public void shouldOnCreateButtonPressedDoNothingIfSnEmpty() {
        //given
        panel.getCnTextBox()
             .setText("cn value");
        panel.getSnTextBox()
             .setText("");
        //when
        panel.onCreateButtonPressed();
        //then
        then(logMessages).should(never())
                         .add(anyString());
        then(applicationEventPublisher).should(never())
                                       .publishEvent(any(CreateUserCommitEvent.class));
    }

    @Test
    public void shouldOnCreateButtonPressedPublishIfValuesSet() {
        //given
        panel.getCnTextBox()
             .setText("cn value");
        panel.getSnTextBox()
             .setText("sn value");
        //when
        panel.onCreateButtonPressed();
        //then
        then(logMessages).should()
                         .add(anyString());
        then(applicationEventPublisher).should()
                                       .publishEvent(eventArgumentCaptor.capture());
        val event = eventArgumentCaptor.getValue();
        assertThat(event.getCn()).isEqualTo("cn value");
        assertThat(event.getSn()).isEqualTo("sn value");
    }
}
