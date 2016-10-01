package net.kemitix.ldapmanager.events;

import com.googlecode.lanterna.gui2.BasicWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for .
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class AppExitEventConfigurationIT {

    @Autowired(required = false)
    Set<ApplicationExitRequest.Listener> applicationExitRequestListeners;

    @Autowired
    private EventDispatcher applicationExitRequestDispatcher;

    @Autowired
    private BasicWindow mainWindow;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Test
    public void checkListeners() {
        assertThat(applicationExitRequestListeners).hasSize(2);
    }

    @Test
    @DirtiesContext
    // dirties context by causing the scheduler to be shutdown before it's own test later
    public void appExitHandlerShouldCloseWindow() {
        //given
        assertThat(mainWindow.getComponent()).isNotNull();
        //when
        applicationExitRequestDispatcher.run();
        //then
        assertThat(mainWindow.getComponent()).isNull();
    }

    @Test
    public void appExitHandlerShouldShutdownExecutorService() throws InterruptedException {
        //given
        assertThat(scheduledExecutorService.isShutdown()).isFalse();
        //when
        applicationExitRequestDispatcher.run();
        //then
        assertThat(scheduledExecutorService.isShutdown()).isTrue();
    }
}
