package net.kemitix.ldapmanager.events;

import com.googlecode.lanterna.gui2.BasicWindow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for .
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AppExitEventConfigurationIT {

    @Autowired
    List<ApplicationListener<ApplicationExitRequest.Event>> appExitListeners;

    @Autowired
    private Runnable appExitHandler;

    @Autowired
    private BasicWindow mainWindow;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Before
    public void setUp() {
        Mockito.reset(scheduledExecutorService, mainWindow);
    }

    @Test
    public void checkListeners() {
        assertThat(appExitListeners).hasSize(2);
    }

    @Test
    public void appExitHandlerShouldCloseWindow() {
        //when
        appExitHandler.run();
        //then
        then(mainWindow).should()
                        .close();
    }

    @Test
    public void appExitHandlerShouldShutdownExecutorService() {
        //when
        appExitHandler.run();
        //then
        then(scheduledExecutorService).should()
                                      .shutdown();
    }
}
