package net.kemitix.ldapmanager.events;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link NavigationItemOuSelectedEvent}.
 */
public class NavigationItemOuSelectedEventTest {

    @Test
    public void of() {
        //given
        val ouNavigationItem = mock(OuNavigationItem.class);
        //when
        val result = NavigationItemOuSelectedEvent.of(ouNavigationItem);
        //then
        assertThat(result.getOuNavigationItem()).isSameAs(ouNavigationItem);
    }

    @Test
    public void getDn() {
        //given
        val dn = mock(Name.class);
        val ouNavigationItem = OuNavigationItem.of(OU.of(dn, "ou"), mock(ApplicationEventPublisher.class));
        val subject = NavigationItemOuSelectedEvent.of(ouNavigationItem);
        //when
        val result = subject.getDn();
        //then
        assertThat(result).isSameAs(dn);
    }
}
