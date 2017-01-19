package net.kemitix.ldapmanager.actions.user.password;

import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link DefaultPasswordDialog}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultPasswordDialogTest {

    private DefaultPasswordDialog dialog;

    @Mock
    private WindowBasedTextGUI gui;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dialog = new DefaultPasswordDialog(gui);
    }

    @Test
    public void shouldGetPassword() throws Exception {
        //when
        dialog.getPassword("description");
        //then
        then(gui).should()
                 .addWindow(any(Window.class));
    }
}
