package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.events.NavigationItemUserActionEvent;
import net.kemitix.ldapmanager.events.NavigationItemUserSelectedEvent;
import net.kemitix.ldapmanager.events.RenameRequestEvent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link UserNavigationItem}.
 */
public class UserNavigationItemTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void builder() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        //when
        val result = UserNavigationItem.builder()
                                       .user(user)
                                       .applicationEventPublisher(applicationEventPublisher)
                                       .build();
        //then
        assertThat(result).isInstanceOf(UserNavigationItem.class);
        assertThat(result.getUser()).isSameAs(user);
        assertThat(result.getApplicationEventPublisher()).isSameAs(applicationEventPublisher);
    }

    @Test
    public void create() throws Exception {
        //given
        val user = mock(User.class);
        given(user.name()).willReturn("name");
        given(user.getDn()).willReturn(mock(Name.class));
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        //when
        val result = UserNavigationItem.create(user, applicationEventPublisher);
        //then
        assertThat(result).isInstanceOf(UserNavigationItem.class);
        assertThat(result.getUser()).isSameAs(user);
        assertThat(result.getApplicationEventPublisher()).isSameAs(applicationEventPublisher);
    }

    @Test
    public void createWithNullUser() throws Exception {
        //given
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        exception.expect(NullPointerException.class);
        exception.expectMessage("user");
        //when
        UserNavigationItem.create(null, applicationEventPublisher);
    }

    @Test
    public void run() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = UserNavigationItem.builder()
                                        .user(user)
                                        .applicationEventPublisher(applicationEventPublisher)
                                        .build();
        //when
        subject.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserActionEvent.class));
    }

    @Test
    public void publishAsSelected() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = UserNavigationItem.builder()
                                        .user(user)
                                        .applicationEventPublisher(applicationEventPublisher)
                                        .build();
        //when
        subject.publishAsSelected();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemUserSelectedEvent.class));
    }

    @Test
    public void publishRenameRequest() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = UserNavigationItem.builder()
                                        .user(user)
                                        .applicationEventPublisher(applicationEventPublisher)
                                        .build();
        //when
        subject.publishRenameRequest();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameRequestEvent.class));
    }

    @Test
    public void publishChangePasswordRequest() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = UserNavigationItem.builder()
                                        .user(user)
                                        .applicationEventPublisher(applicationEventPublisher)
                                        .build();
        //when
        subject.publishChangePasswordRequest();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(ChangePasswordRequestEvent.class));
    }

    @Test
    public void hasFeature() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        given(user.hasFeature(Features.PASSWORD)).willReturn(true);
        given(user.hasFeature(Features.RENAME)).willReturn(false);
        val subject = UserNavigationItem.builder()
                                        .user(user)
                                        .applicationEventPublisher(applicationEventPublisher)
                                        .build();
        //when
        val resultTrue = subject.hasFeature(Features.PASSWORD);
        val resultFalse = subject.hasFeature(Features.RENAME);
        //then
        assertThat(resultTrue).isTrue();
        assertThat(resultFalse).isFalse();
    }

    @Test
    public void getSortableType() throws Exception {
        //given
        val user = mock(User.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        given(user.getFeatureSet()).willReturn(EnumSet.of(Features.PASSWORD));
        val subject = UserNavigationItem.builder()
                                        .user(user)
                                        .applicationEventPublisher(applicationEventPublisher)
                                        .build();
        //when
        val result = subject.getSortableType();
        //then
        assertThat(result).isEqualTo("2:user");
    }
}
