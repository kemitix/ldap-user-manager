package net.kemitix.ldapmanager.state;

import net.kemitix.ldapmanager.ldap.LdapService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link CurrentContainerLoader}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CurrentContainerLoaderTest {

    private CurrentContainerLoader currentContainerLoader;

    @Mock
    private LdapService ldapService;

    @Mock
    private LdapEntityContainerMap ldapEntityContainerMap;

    @Mock
    private CurrentContainer currentContainer;

    @Mock
    private LogMessages logMessages;

    private Name dn;

    @Mock
    private LdapEntityContainer container;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        currentContainerLoader =
                new CurrentContainerLoader(ldapService, ldapEntityContainerMap, currentContainer, logMessages);
        dn = LdapNameBuilder.newInstance("cn=bob")
                            .build();
        given(currentContainer.getDn()).willReturn(dn);
        given(ldapService.getLdapEntityContainer(dn)).willReturn(container);
    }

    @Test
    public void shouldOnCurrentContainerChangedEventLoadLdapContainerWhenContainerNotFound() throws Exception {
        //given
        given(ldapEntityContainerMap.get(dn)).willReturn(Optional.empty());
        //when
        currentContainerLoader.onCurrentContainerChangedEventLoadLdapContainer();
        //then
        then(ldapEntityContainerMap).should()
                                    .put(dn, container);
        then(ldapService).should().getLdapEntityContainer(dn);
    }

    @Test
    public void shouldOnCurrentContainerChangedEventLoadLdapContainerWhenContainerFound() throws Exception {
        //given
        given(ldapEntityContainerMap.get(dn)).willReturn(Optional.of(container));
        //when
        currentContainerLoader.onCurrentContainerChangedEventLoadLdapContainer();
        //then
        then(ldapEntityContainerMap).should()
                                    .put(dn, container);
        then(ldapService).should().getLdapEntityContainer(dn);
    }
}
