package net.kemitix.ldapmanager.ui.events;

import com.googlecode.lanterna.gui2.BasicWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.then;

/**
 * Tests for .
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class AppExitEventConfigurationIT {

    @Autowired
    private Runnable appExitHandler;

    @Autowired
    private BasicWindow window;

    @Test
    public void appExitHandlerShouldTriggerWindowClose() {
        //when
        appExitHandler.run();
        //then
        then(window).should().close();
    }
}
