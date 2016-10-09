package net.kemitix.ldapmanager.state;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import net.kemitix.ldapmanager.ldap.LdapService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link DefaultLdapEntityContainerMap}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLdapEntityContainerMapTest {

    private DefaultLdapEntityContainerMap containerMap;

    private Name dn;

    @Mock
    private LdapService ldapService;

    @Mock
    private LdapEntityContainer container;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        containerMap = new DefaultLdapEntityContainerMap(ldapService);
        dn = LdapNameUtil.parse("ou=users");
    }

    @Test
    public void getShouldQueryLdapServiceIfNotFound() {
        //given
        containerMap.clear();
        //when
        containerMap.get(dn);
        //then
        then(ldapService).should()
                         .getLdapEntityContainer(dn);
    }

    @Test
    public void getShouldReturnItemLdapServiceProvides() {
        //given
        given(ldapService.getLdapEntityContainer(dn)).willReturn(container);
        //when
        val result = containerMap.get(dn);
        //then
        assertThat(result).isSameAs(container);
    }

    @Test
    public void getShouldReturnSameItemOnSubsequentCall() throws Exception {
        //given
        given(ldapService.getLdapEntityContainer(dn)).willReturn(container);
        containerMap.get(dn);
        //when
        val result = containerMap.get(dn);
        //then
        assertThat(result).isSameAs(container);
    }
}
