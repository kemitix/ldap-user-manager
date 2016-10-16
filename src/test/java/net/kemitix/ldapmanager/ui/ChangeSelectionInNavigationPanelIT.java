package net.kemitix.ldapmanager.ui;

import net.kemitix.ldapmanager.domain.LdapEntity;
import net.kemitix.ldapmanager.ldap.AbstractLdapConnectionIntegrationTest;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.LdapOptions;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import net.kemitix.ldapmanager.navigation.ui.NavigationItemActionListBox;
import net.kemitix.ldapmanager.navigation.events.NavigationItemSelectionChangedEvent;
import net.kemitix.ldapmanager.navigation.OuNavigationItem;
import net.kemitix.ldapmanager.navigation.UserNavigationItem;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for navigating within a single container.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "ldap-connection-it"})
public class ChangeSelectionInNavigationPanelIT extends AbstractLdapConnectionIntegrationTest {

    private static final String OU_NAME = "test1";

    private static final String USER_NAME = "gary";

    @Autowired
    private CurrentContainer currentContainer;

    @Autowired
    private Supplier<LdapEntityContainer> ldapEntityContainerSupplier;

    @Autowired
    private CurrentOuLabel currentOuLabel;

    @Autowired
    private NavigationItemActionListBox navigationItemActionListBox;

    @Autowired
    private LdapOptions ldapOptions;

    @Autowired
    private List<NavigationItemSelectionChangedEvent> navigationItemSelectionChangedEvents;

    @Captor
    private ArgumentCaptor<NavigationItemSelectionChangedEvent> eventArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currentContainer.setDn(LdapNameUtil.empty());
        currentContainer.publishCurrentContainer();
        navigationItemSelectionChangedEvents.clear();
    }

    @Test
    public void verifyStartConditions() {
        assertThat(currentOuLabel.getText()).isEqualTo(ldapOptions.getBase());
        assertThat(ldapEntityContainerSupplier.get()
                                              .getContents()).extracting(LdapEntity::name)
                                                             .containsExactly(OU_NAME, USER_NAME);
        assertThat(navigationItemActionListBox.getSelectedIndex()).isEqualTo(0);
        assertThat(navigationItemActionListBox.getSelectedItem()
                                              .getName()).isEqualTo(OU_NAME);
    }

    @Test
    public void whenNavigateToUserShouldPublishChangeEvent() {
        //when
        navigationItemActionListBox.findAndSelectItemByName(USER_NAME);
        //then
        assertThat(navigationItemSelectionChangedEvents).hasSize(1);
        final NavigationItemSelectionChangedEvent event = navigationItemSelectionChangedEvents.get(0);

        // the item being deselected
        final Optional<NavigationItem> oldItemOptional = event.getOldItem();
        assertThat(oldItemOptional).isNotEmpty();
        oldItemOptional.ifPresent(oldItem -> {
//            assertThat(oldItem).isInstanceOf(OuNavigationItem.class);
            final OuNavigationItem ouNavigationItem = (OuNavigationItem) oldItem;
            assertThat(ouNavigationItem.getOu()
                                       .name()).isEqualTo(OU_NAME);
        });

        // the item being selected
        final Optional<NavigationItem> newItemOptional = event.getNewItem();
        assertThat(newItemOptional).isNotEmpty();
        newItemOptional.ifPresent(newItem -> {
            assertThat(newItem).isInstanceOf(UserNavigationItem.class);
            final UserNavigationItem userNavigationItem = (UserNavigationItem) newItem;
            assertThat(userNavigationItem.getUser()
                                         .name()).isEqualTo(USER_NAME);
        });
    }

    @Test
    public void whenNavigateToOuShouldPublishChangeEvent() {
        //when
        navigationItemActionListBox.findAndSelectItemByName(USER_NAME);
        navigationItemActionListBox.findAndSelectItemByName(OU_NAME);
        //then
        // first event is selecting the user, the second is the user. We want the second
        assertThat(navigationItemSelectionChangedEvents).hasSize(2);
        final NavigationItemSelectionChangedEvent event = navigationItemSelectionChangedEvents.get(1);

        // the item being deselected
        final Optional<NavigationItem> oldItemOptional = event.getOldItem();
        assertThat(oldItemOptional).isNotEmpty();
        oldItemOptional.ifPresent(oldItem -> {
            assertThat(oldItem).isInstanceOf(UserNavigationItem.class);
            final UserNavigationItem userNavigationItem = (UserNavigationItem) oldItem;
            assertThat(userNavigationItem.getUser()
                                         .name()).isEqualTo(USER_NAME);
        });

        // the item being selected
        final Optional<NavigationItem> newItemOptional = event.getNewItem();
        assertThat(newItemOptional).isNotEmpty();
        newItemOptional.ifPresent(newItem -> {
            assertThat(newItem).isInstanceOf(OuNavigationItem.class);
            final OuNavigationItem ouNavigationItem = (OuNavigationItem) newItem;
            assertThat(ouNavigationItem.getOu()
                                       .name()).isEqualTo(OU_NAME);
        });
    }
}
