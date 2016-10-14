package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.navigation.events.NavigationItemOuActionEvent;
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

    private OU usersOu;

    private OU itUsersOu;

    private User bobUsersUser;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        navigationItemsSupplier =
                new NavigationItemsSupplier(currentLdapContainerSupplier, currentContainer, eventPublisher);
        usersOu = OU.builder()
                    .dn(LdapNameUtil.parse("ou=users"))
                    .ou("users")
                    .build();
        itUsersOu = OU.builder()
                      .dn(LdapNameUtil.parse("ou=it,ou=users"))
                      .ou("it")
                      .build();
        bobUsersUser = User.builder()
                           .dn(LdapNameUtil.parse("cn=bob,ou=users"))
                           .cn("bob")
                           .build();
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        given(currentContainer.getDn()).willReturn(usersOu.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(
                LdapEntityContainer.of(Collections.singletonList(bobUsersUser)));
        //when
        val result = navigationItemsSupplier.get();
        //then
        assertThat(result).as("get the user bob and the injected '..' for the parent OU")
                          .hasSize(2);
        /// the injected parent '..'
        assertThat(result.get(0)
                         .getName()).isEqualTo("..");
        /// the user bob
        assertThat(result.get(1)
                         .getName()).isEqualTo("bob");
    }

    @Test
    public void shouldPublishSwitchOuWhenRunOuNavigationItem() {
        //given
        given(currentContainer.getDn()).willReturn(usersOu.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(
                LdapEntityContainer.of(Arrays.asList(itUsersOu, bobUsersUser)));
        //when
        navigationItemsSupplier.get()
                               .stream()
                               .filter(namedItem -> namedItem.getName()
                                                             .equals(itUsersOu.name()))
                               .map(NamedItem::getItem)
                               .forEach(Runnable::run);
        //then
        val event = ArgumentCaptor.forClass(NavigationItemOuActionEvent.class);
        then(eventPublisher).should()
                            .publishEvent(event.capture());
        assertThat(event.getValue()
                        .getOu()).isSameAs(itUsersOu);
    }

    @Test
    public void getShouldReturnRootWhenDnIsEmpty() {
        //given
        given(currentContainer.getDn()).willReturn(LdapNameUtil.empty());
        given(currentLdapContainerSupplier.get()).willReturn(
                LdapEntityContainer.of(Collections.singletonList(bobUsersUser)));
        //when
        final List<NamedItem<NavigationItem>> namedItems = navigationItemsSupplier.get();
        //then
        assertThat(namedItems.stream()
                             .map(NamedItem::getName)).containsOnly(bobUsersUser.name());
    }

    @Test
    public void injectedParentItemsShouldPublishSwitchOuEventWhenRun() {
        //given
        given(currentContainer.getDn()).willReturn(itUsersOu.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(
                LdapEntityContainer.of(Collections.singletonList(bobUsersUser)));
        val namedItem = navigationItemsSupplier.get()
                                               .get(0);
        assertThat(namedItem.getName()).as("first item is '..'")
                                       .isEqualTo("..");
        final NavigationItem navigationItem = namedItem.getItem();
        assertThat(navigationItem).isInstanceOf(OuNavigationItem.class);
        val ouNavigationItem = (OuNavigationItem) navigationItem;
        assertThat(ouNavigationItem.getOu()
                                   .getDn()).isEqualTo(usersOu.getDn());
        //when
        navigationItem.run();
        //then
        val eventCaptor = ArgumentCaptor.forClass(NavigationItemOuActionEvent.class);
        then(eventPublisher).should()
                            .publishEvent(eventCaptor.capture());
        assertThat(eventCaptor.getValue()
                              .getOu()
                              .getDn()).isEqualTo(usersOu.getDn());
    }
}
