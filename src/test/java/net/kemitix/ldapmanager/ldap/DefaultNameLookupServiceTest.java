package net.kemitix.ldapmanager.ldap;

import lombok.val;
import net.kemitix.ldapmanager.domain.LdapEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Name;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;

/**
 * Tests for {@link DefaultNameLookupService}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultNameLookupServiceTest {

    private DefaultNameLookupService lookupService;

    @Mock
    private LdapTemplate ldapTemplate;

    private Set<PreCheckContextMapper<? extends LdapEntity>> contextMappers;

    @Mock
    private PreCheckContextMapper<LdapEntity> mapper1;

    @Mock
    private PreCheckContextMapper<LdapEntity> mapper2;

    @Mock
    private DirContextOperations context;

    @Mock
    private Name dn;

    @Mock
    private LdapEntity entity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        contextMappers = new HashSet<>();
        contextMappers.add(mapper1);
        contextMappers.add(mapper2);
        lookupService = new DefaultNameLookupService(ldapTemplate, contextMappers);
        given(ldapTemplate.lookupContext(dn)).willReturn(context);
    }

    @Test
    public void findByDnShouldFindEitherMapperWhenBothCanMap() throws NamingException {
        //given
        givenMapperCanMap(mapper1, true);
        givenMapperCanMap(mapper2, true);
        //when
        val result = lookupService.findByDn(dn);
        //then
        assertThat(result).contains(entity);
    }

    @Test
    public void findByDnShouldFindMapper1WhenMapper2CanNotMap() throws NamingException {
        //given
        givenMapperCanMap(mapper1, true);
        givenMapperCanMap(mapper2, false);
        //when
        val result = lookupService.findByDn(dn);
        //then
        then(mapper2).should(never())
                     .mapFromContext(any());
        assertThat(result).contains(entity);
    }

    @Test
    public void findByDnShouldFindMapper2WhenMapper1CanNotMap() throws NamingException {
        //given
        givenMapperCanMap(mapper1, false);
        givenMapperCanMap(mapper2, true);
        //when
        val result = lookupService.findByDn(dn);
        //then
        then(mapper1).should(never())
                     .mapFromContext(any());
        assertThat(result).contains(entity);
    }

    @Test
    public void findByDnShouldReturnEmptyWhenNoMapperCanNotMap() throws NamingException {
        //given
        givenMapperCanMap(mapper1, false);
        givenMapperCanMap(mapper2, false);
        //when
        val result = lookupService.findByDn(dn);
        //then
        then(mapper1).should(never())
                     .mapFromContext(any());
        then(mapper2).should(never())
                     .mapFromContext(any());
        assertThat(result).isEmpty();
    }

    @Test
    public void findByDnShouldReturnEmptyIfNamingExceptionInMapper() throws NamingException {
        //given
        given(mapper1.canMapContext(context)).willReturn(true);
        doThrow(NamingException.class).when(mapper1)
                                      .mapFromContext(context);
        given(mapper2.canMapContext(context)).willReturn(false);
        //when
        val result = lookupService.findByDn(dn);
        //then
        assertThat(result).isEmpty();
    }

    private void givenMapperCanMap(final PreCheckContextMapper<LdapEntity> mapper, final boolean canMap)
            throws NamingException {
        given(mapper.canMapContext(context)).willReturn(canMap);
        if (canMap) {
            given(mapper.mapFromContext(context)).willReturn(entity);
        } else {
            given(mapper.mapFromContext(context)).willReturn(null);
        }
    }
}
