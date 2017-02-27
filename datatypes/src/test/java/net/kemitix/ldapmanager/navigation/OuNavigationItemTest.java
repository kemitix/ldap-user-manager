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
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        //when
        val result = OuNavigationItem.builder()
                                     .ou(ou)
                                     .applicationEventPublisher(applicationEventPublisher)
                                     .build();
        //then
        assertThat(result).isInstanceOf(OuNavigationItem.class);
        assertThat(result.getOu()).isSameAs(ou);
        assertThat(result.getApplicationEventPublisher()).isSameAs(applicationEventPublisher);
        assertThat(result.getDn()).isSameAs(dn);
    }

    @Test
    public void of() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val name = ou.name();
        //when
        val result = OuNavigationItem.of(ou, applicationEventPublisher);
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
        OuNavigationItem.of(null, applicationEventPublisher);
    }

    @Test
    public void run() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        subject.run();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemOuActionEvent.class));
    }

    @Test
    public void publishAsSelected() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        subject.publishAsSelected();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(NavigationItemOuSelectedEvent.class));
    }

    @Test
    public void publishRenameRequest() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        subject.publishRenameRequest();
        //then
        then(applicationEventPublisher).should()
                                       .publishEvent(any(RenameRequestEvent.class));
    }

    @Test
    public void publishChangePasswordRequest() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        subject.publishChangePasswordRequest();
        //then
        then(applicationEventPublisher).should(never())
                                       .publishEvent(any(ChangePasswordRequestEvent.class));
    }

    @Test
    public void hasFeature() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        val resultTrue = subject.hasFeature(Features.RENAME);
        val resultFalse = subject.hasFeature(Features.PASSWORD);
        //then
        assertThat(resultTrue).isTrue();
        assertThat(resultFalse).isFalse();
    }

    @Test
    public void getSortableType() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        val result = subject.getSortableType();
        //then
        assertThat(result).isEqualTo("1:ou");
    }

    @Test
    public void getLabel() throws Exception {
        //given
        val dn = mock(Name.class);
        val ou = OU.of(dn, "ou");
        val applicationEventPublisher = mock(ApplicationEventPublisher.class);
        val expected = "[ou]";
        val subject = OuNavigationItem.of(ou, applicationEventPublisher);
        //when
        val result = subject.getLabel();
        //then
        assertThat(result).isEqualTo(expected);
    }
}
