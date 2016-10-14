package net.kemitix.ldapmanager.navigation;

import net.kemitix.ldapmanager.navigation.events.NavigationItemSelectionChangedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @author
 */
@Profile("test")
@Configuration
public class NavigationTestConfiguration {

    private List<NavigationItemSelectionChangedEvent> navigationItemSelectionChangedEvents = new ArrayList<>();

    @EventListener(NavigationItemSelectionChangedEvent.class)
    public void navigationItemSelectionChangedEventListener(final NavigationItemSelectionChangedEvent event) {
        navigationItemSelectionChangedEvents.add(event);
    }

    @Bean
    public List<NavigationItemSelectionChangedEvent> navigationItemSelectionChangedEvents() {
        return navigationItemSelectionChangedEvents;
    }
}
