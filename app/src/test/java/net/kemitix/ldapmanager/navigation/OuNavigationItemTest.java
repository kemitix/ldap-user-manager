package net.kemitix.ldapmanager.navigation;

import net.kemitix.ldapmanager.events.RenameRequestEvent;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.navigation.events.NavigationItemOuSelectedEvent;
import net.kemitix.ldapmanager.navigation.events.NavigationItemUserSelectedEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link OuNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class OuNavigationItemTest {

    private NavigationItem ouNavigationItem;

    private OU ou;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private String name;

    @Mock
    private Name dn;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        name = "name";
        ou = OU.of(dn, name);
        ouNavigationItem = OuNavigationItem.create(ou, applicationEventPublisher);
    }

    @Test
    public void create() throws Exception {
        assertThat(ouNavigationItem).isInstanceOf(OuNavigationItem.class);
    }

    @Test
    public void createWithNullUserThrowsNPE() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("ou");
        //when
        OuNavigationItem.create(null, applicationEventPublisher);
    }

    @Test
    public void createWithNullEventPublisherThrowsNPE() throws Exception {
        //given
        exception.expect(NullPointerException.class);
        exception.expectMessage("eventPublisher");
        //when
        OuNavigationItem.create(ou, null);
    }


    @Test
    public void run() throws Exception {
        //when
        ouNavigationItem.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserSelectedEvent.class));
    }

    @Test
    public void getOu() throws Exception {
        assertThat(((OuNavigationItem) ouNavigationItem).getOu()).isSameAs(ou);
    }

    @Test
    public void toStringContainsName() throws Exception {
        assertThat(ouNavigationItem.toString()).contains(name);
    }

    @Test
    public void publishAsSelectedShouldPublishEvent() throws Exception {
        //when
        ouNavigationItem.publishAsSelected();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemOuSelectedEvent.class));
    }

    @Test
    public void publishRenameRequestShouldPublishEvent() {
        //when
        ouNavigationItem.publishRenameRequest();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameRequestEvent.class));
    }

    @Test
    public void publishChangePasswordRequestShouldPublishEvent() {
        //when
        ouNavigationItem.publishChangePasswordRequest();
        //then
        then(applicationEventPublisher).should(never())
                                       .publishEvent(any(ChangePasswordRequestEvent.class));
    }

    @Test
    public void shouldHaveFeatureRename() {
        assertThat(ouNavigationItem.hasFeature(Features.RENAME)).isTrue();
    }

    @Test
    public void shouldNotHaveFeaturePassword() {
        assertThat(ouNavigationItem.hasFeature(Features.PASSWORD)).isFalse();
    }
}
