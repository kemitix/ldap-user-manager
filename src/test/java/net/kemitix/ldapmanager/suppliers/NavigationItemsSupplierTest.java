package net.kemitix.ldapmanager.suppliers;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.NavigationItemSelectedEvent;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link NavigationItemsSupplier}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemsSupplierTest {

    private NavigationItemsSupplier supplier;

    @Mock
    private Supplier<LdapEntityContainer> currentLdapContainerSupplier;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        supplier = new NavigationItemsSupplier(currentLdapContainerSupplier, eventPublisher);
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Collections.singletonList(
                User.builder()
                    .cn("user name")
                    .build())));
        //when
        val result = supplier.get();
        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)
                         .getName()).isEqualTo("user name");
    }

    @Test
    public void shouldPublishNavigationItemSelectedEventWhenItemSelected() {
        //given
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Collections.singletonList(
                User.builder()
                    .cn("user name")
                    .build())));
        final List<NamedItem<Runnable>> namedItems = supplier.get();
        //when
        namedItems.stream()
                  .map(NamedItem::getItem)
                  .forEach(Runnable::run);
        //then
        then(eventPublisher).should()
                            .publishEvent(any(NavigationItemSelectedEvent.class));
    }
}
