package net.kemitix.ldapmanager.actions.rename;

import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.events.ContainerExpiredEvent;
import net.kemitix.ldapmanager.ui.RenameDnDialog;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Name;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests for {@link RenameAction}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RenameActionTest {

    private RenameAction renameAction;

    @Mock
    private RenameDnDialog renameDnDialog;

    @Mock
    private LdapTemplate ldapTemplate;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private Name oldDn;

    @Mock
    private Name newDn;

    @Captor
    private ArgumentCaptor<ContainerExpiredEvent> eventArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        renameAction = new RenameAction(renameDnDialog, ldapTemplate, applicationEventPublisher);
    }

    @Test
    public void whenNewDnThenSetPasswordAndPublish() throws Exception {
        //given
        oldDn=LdapNameUtil.parse("cn=bob,ou=users");
        newDn=LdapNameUtil.parse("cn=bobby,ou=users");
        given(renameDnDialog.getRenamedDn(oldDn)).willReturn(Optional.of(newDn));
        //when
        renameAction.onRenameRequest(RenameRequestEvent.of(oldDn));
        //then
        then(ldapTemplate).should()
                          .rename(oldDn, newDn);
    }

    @Test
    public void whenNoNewDnThenDoNotUseLdapTemplateOrPublisher() throws Exception {
        //given
        given(renameDnDialog.getRenamedDn(oldDn)).willReturn(Optional.empty());
        //when
        renameAction.onRenameRequest(RenameRequestEvent.of(oldDn));
        //then
        verifyNoMoreInteractions(ldapTemplate);
    }

    @Test
    public void shouldExpireOneContainerWhenRenamingLdapEntity() throws Exception {
        //given
        oldDn = LdapNameUtil.parse("cn=bob,ou=users");
        newDn = LdapNameUtil.parse("cn=bobby,ou=users");
        given(renameDnDialog.getRenamedDn(oldDn)).willReturn(Optional.of(newDn));
        //when
        renameAction.onRenameRequest(RenameRequestEvent.of(oldDn));
        //then
        then(ldapTemplate).should()
                          .rename(oldDn, newDn);
        then(applicationEventPublisher).should()
                                       .publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()
                                      .getContainers()).containsOnly(LdapNameUtil.parse("ou=users"));
    }

    @Test
    public void shouldExpireTwoContainerWhenMovingLdapEntity() throws Exception {
        //given
        oldDn = LdapNameUtil.parse("cn=bob,ou=users");
        newDn = LdapNameUtil.parse("cn=bobby,ou=managers");
        given(renameDnDialog.getRenamedDn(oldDn)).willReturn(Optional.of(newDn));
        //when
        renameAction.onRenameRequest(RenameRequestEvent.of(oldDn));
        //then
        then(ldapTemplate).should()
                          .rename(oldDn, newDn);
        then(applicationEventPublisher).should()
                                       .publishEvent(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue()
                                      .getContainers()).containsOnly(LdapNameUtil.parse("ou=users"),
                                                                     LdapNameUtil.parse("ou=managers")
                                                                    );
    }
}
