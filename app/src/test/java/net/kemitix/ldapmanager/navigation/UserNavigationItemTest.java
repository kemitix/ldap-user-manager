package net.kemitix.ldapmanager.navigation;

import net.kemitix.ldapmanager.actions.rename.RenameRequestEvent;
import net.kemitix.ldapmanager.actions.user.password.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserActionEvent;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserSelectedEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link UserNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class UserNavigationItemTest {

    private NavigationItem userNavigationItem;

    private User user;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private String name;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        name = "name";
        user = User.builder()
                   .dn(LdapNameUtil.parse("cn=name"))
                   .cn(name)
                   .build();
        userNavigationItem = UserNavigationItem.create(user, applicationEventPublisher);
    }

    @Test
    public void create() throws Exception {
        assertThat(userNavigationItem).isInstanceOf(UserNavigationItem.class);
    }

    @Test
    public void createWithNullUserThrowsNPE() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("user");
        //when
        UserNavigationItem.create(null, applicationEventPublisher);
    }

    @Test
    public void createWithNullEventPublisherThrowsNPE() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("eventPublisher");
        //when
        UserNavigationItem.create(user, null);
    }

    @Test
    public void run() throws Exception {
        //when
        userNavigationItem.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserActionEvent.class));
    }

    @Test
    public void getUser() throws Exception {
        assertThat(((UserNavigationItem) userNavigationItem).getUser()).isSameAs(user);
    }

    @Test
    public void toStringContainsName() throws Exception {
        assertThat(userNavigationItem.toString()).contains(name);
    }

    @Test
    public void publishAsSelectedShouldPublishEvent() throws Exception {
        //when
        userNavigationItem.publishAsSelected();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserSelectedEvent.class));
    }

    @Test
    public void publishRenameRequestShouldPublishEvent() {
        //when
        userNavigationItem.publishRenameRequest();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameRequestEvent.class));
    }

    @Test
    public void publishChangePasswordRequestShouldPublishEvent() {
        //when
        userNavigationItem.publishChangePasswordRequest();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(ChangePasswordRequestEvent.class));
    }

    @Test
    public void shouldHaveFeatureRename() {
        assertThat(userNavigationItem.hasFeature(Features.RENAME)).isTrue();
    }

    @Test
    public void shouldHaveFeaturePassword() {
        assertThat(userNavigationItem.hasFeature(Features.PASSWORD)).isTrue();
    }
}
