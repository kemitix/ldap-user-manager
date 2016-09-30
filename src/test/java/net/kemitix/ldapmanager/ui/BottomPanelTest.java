package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import net.kemitix.ldapmanager.events.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link BottomPanel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class BottomPanelTest {

    private BottomPanel bottomPanel;

    @Mock
    private EventDispatcher appExitHandler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bottomPanel = new BottomPanel(appExitHandler);
    }

    @Test
    public void shouldInit() throws Exception {
        //when
        bottomPanel.init();
        //then
        bottomPanel.getChildren()
                   .forEach(child -> {
                       assertThat(child).isInstanceOf(Border.class);
                       Border border = (Border) child;
                       assertThat(border.getComponent()).isInstanceOf(Panel.class);
                       Panel panel = (Panel) border.getComponent();
                       panel.getChildren()
                            .forEach(innerChild -> {
                                assertThat(innerChild).isInstanceOf(Button.class);
                                Button button = (Button) innerChild;
                                assertThat(button.getLabel()).isEqualTo("Exit");
                                //when
                                button.handleKeyStroke(new KeyStroke(KeyType.Enter));
                                //then
                                then(appExitHandler).should()
                                                    .run();
                            });
                   });
    }
}
