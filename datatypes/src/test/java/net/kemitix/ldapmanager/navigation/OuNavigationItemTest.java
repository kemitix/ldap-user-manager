package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.events.ChangePasswordRequestEvent;
import net.kemitix.ldapmanager.events.NavigationItemOuActionEvent;
import net.kemitix.ldapmanager.events.NavigationItemOuSelectedEvent;
import net.kemitix.ldapmanager.events.RenameRequestEvent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationEventPublisher;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link OuNavigationItem}.
 */
public class OuNavigationItemTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void builder() throws Exception {
        //given
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        //when
        val result = OuNavigationItem.builder()
                                     .ou(ou)
                                     .applicationEventPublisher(applicationEventPublisher)
                                     .dn(dn)
                                     .name(name)
                                     .build();
        //then
        assertThat(result).isInstanceOf(OuNavigationItem.class);
        assertThat(result.getOu()).isSameAs(ou);
        assertThat(result.getApplicationEventPublisher()).isSameAs(applicationEventPublisher);
        assertThat(result.getDn()).isSameAs(dn);
        assertThat(result.getName()).isSameAs(name);
    }

    @Test
    public void create() throws Exception {
        //given
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        given(ou.getOu()).willReturn(name);
        given(ou.getDn()).willReturn(dn);
        //when
        val result = OuNavigationItem.create(ou, applicationEventPublisher);
        //then
        assertThat(result).isInstanceOf(OuNavigationItem.class);
        assertThat(result.getOu()).isSameAs(ou);
        assertThat(result.getApplicationEventPublisher()).isSameAs(applicationEventPublisher);
        assertThat(result.getDn()).isSameAs(dn);
        assertThat(result.getName()).isSameAs(name);
    }

    @Test
    public void createWhenOUIsNull() throws Exception {
        //given
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        exception.expect(NullPointerException.class);
        exception.expectMessage("ou");
        //when
        OuNavigationItem.create(null, applicationEventPublisher);
    }

    @Test
    public void run() throws Exception {
        //given
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
                                      .build();
        //when
        subject.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemOuActionEvent.class));
    }

    @Test
    public void publishAsSelected() throws Exception {
        //given
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
                                      .build();
        //when
        subject.publishAsSelected();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemOuSelectedEvent.class));
    }

    @Test
    public void publishRenameRequest() throws Exception {
        //given
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
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
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
                                      .build();
        //when
        subject.publishChangePasswordRequest();
        //then
        then(applicationEventPublisher).should(never())
                                       .publishEvent(any(ChangePasswordRequestEvent.class));
    }

    @Test
    public void hasFeature() throws Exception {
        //given
        val ou = mock(OU.class);
        given(ou.hasFeature(Features.PASSWORD)).willReturn(true);
        given(ou.hasFeature(Features.RENAME)).willReturn(false);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
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
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
                                      .build();
        //when
        val result = subject.getSortableType();
        //then
        assertThat(result).isEqualTo("1:ou");
    }

    @Test
    public void getLabel() throws Exception {
        //given
        val ou = mock(OU.class);
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val dn = mock(Name.class);
        val name = "name";
        val expected = "[name]";
        val subject = OuNavigationItem.builder()
                                      .ou(ou)
                                      .applicationEventPublisher(applicationEventPublisher)
                                      .dn(dn)
                                      .name(name)
                                      .build();
        //when
        val result = subject.getLabel();
        //then
        assertThat(result).isEqualTo(expected);
    }
}
