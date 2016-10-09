package net.kemitix.ldapmanager.suppliers;

import lombok.val;
import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.NavigationItemSelectedEvent;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.util.nameditem.NamedItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link NavigationItemsSupplier}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemsSupplierTest {

    private NavigationItemsSupplier navigationItemsSupplier;

    @Mock
    private Supplier<LdapEntityContainer> currentLdapContainerSupplier;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private CurrentContainer currentContainer;

    private OU ou;

    private OU childOu;

    private User user;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        navigationItemsSupplier =
                new NavigationItemsSupplier(currentLdapContainerSupplier, eventPublisher, currentContainer);
        ou = OU.builder()
               .dn(LdapNameUtil.parse("ou=users"))
               .ou("users")
               .build();
        childOu = OU.builder()
                    .dn(LdapNameUtil.parse("ou=it,ou=users"))
                    .ou("it")
                    .build();
        user = User.builder()
                   .dn(LdapNameUtil.parse("cn=bob,ou=users"))
                   .cn("bob")
                   .build();
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        given(currentContainer.getDn()).willReturn(ou.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Collections.singletonList(user)));
        //when
        val result = navigationItemsSupplier.get();
        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0)
                         .getName()).isEqualTo("..");
        assertThat(result.get(1)
                         .getName()).isEqualTo("bob");
    }

    @Test
    public void shouldPublishNavigationItemSelectedEventWhenOUSelected() {
        //given
        given(currentContainer.getDn()).willReturn(ou.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Arrays.asList(childOu, user)));
        //when
        navigationItemsSupplier.get()
                               .stream()
                               .filter(i -> i.getName()
                                             .equals(childOu.name()))
                               .map(NamedItem::getItem)
                               .forEach(Runnable::run);
        //then
        val event = ArgumentCaptor.forClass(NavigationItemSelectedEvent.class);
        then(eventPublisher).should()
                            .publishEvent(event.capture());
        assertThat(event.getValue()
                        .getSelected()).isSameAs(childOu);
    }

    @Test
    public void getShouldReturnRootWhenDnIsEmpty() {
        //given
        given(currentContainer.getDn()).willReturn(LdapNameUtil.empty());
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Collections.singletonList(user)));
        //when
        final List<NamedItem<Runnable>> namedItems = navigationItemsSupplier.get();
        //then
        assertThat(namedItems.stream()
                             .map(NamedItem::getName)).containsOnly(user.name());
    }

    @Test
    public void injectedParentItemsShouldPublishNavigationItemSelectedEventWhenRun() {
        //given
        given(currentContainer.getDn()).willReturn(ou.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Arrays.asList(childOu, user)));
        val namedItem = navigationItemsSupplier.get()
                                               .get(0);
        assertThat(namedItem.getName()).as("first item is '..'")
                                       .isEqualTo("..");
        namedItem.getItem()
                 //when
                 .run();
        //then
        val event = ArgumentCaptor.forClass(NavigationItemSelectedEvent.class);
        then(eventPublisher).should()
                            .publishEvent(event.capture());
        final NavigationItemSelectedEvent value = event.getValue();
        final LdapEntity selected = value.getSelected();
        final String name = selected.name();
        assertThat(name).isEqualTo("..");
    }
}
