package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.navigation.events.NavigationItemOuActionEvent;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Stream.of(bobUsersUser)));
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
                LdapEntityContainer.of(Stream.of(itUsersOu, bobUsersUser)));
        //when
        navigationItemsSupplier.get()
                               .stream()
                               .filter(namedItem -> namedItem.getName()
                                                             .equals(itUsersOu.name()))
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
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Stream.of(bobUsersUser)));
        //when
        final List<NavigationItem> navigationItems = navigationItemsSupplier.get();
        //then
        assertThat(navigationItems.stream()
                                  .map(NavigationItem::getName)).containsOnly(bobUsersUser.name());
    }

    @Test
    public void injectedParentItemsShouldPublishSwitchOuEventWhenRun() {
        //given
        given(currentContainer.getDn()).willReturn(itUsersOu.getDn());
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Stream.of(bobUsersUser)));
        val navigationItem = navigationItemsSupplier.get()
                                                    .get(0);
        assertThat(navigationItem.getName()).as("first item is '..'")
                                            .isEqualTo("..");
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

    @Test
    public void shouldSortByOusThenByUsers() {
        //given
        val ou1 = OU.builder()
                    .ou("alpha")
                    .dn(LdapNameUtil.parse("ou=alpha"))
                    .build();
        val ou2 = OU.builder()
                    .ou("beta")
                    .dn(LdapNameUtil.parse("ou=beta"))
                    .build();
        val user1 = User.builder()
                        .cn("alice")
                        .dn(LdapNameUtil.parse("cn=alice"))
                        .build();
        val user2 = User.builder()
                        .cn("bob")
                        .dn(LdapNameUtil.parse("cn=bob"))
                        .build();
        val entities = new ArrayList<LdapEntity>(Arrays.asList(ou2, user2, user1, ou1));
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(entities.stream()));
        given(currentContainer.getDn()).willReturn(LdapNameUtil.empty());
        //when
        final List<NavigationItem> navigationItems = navigationItemsSupplier.get();
        //then
        assertThat(navigationItems.stream()
                                  .map(NavigationItem::getName)).containsExactly("alpha", "beta", "alice", "bob");
    }
}
