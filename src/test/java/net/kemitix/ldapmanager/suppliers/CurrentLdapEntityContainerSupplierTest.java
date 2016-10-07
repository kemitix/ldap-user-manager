package net.kemitix.ldapmanager.suppliers;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapService;
import net.kemitix.ldapmanager.state.CurrentContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LdapEntityContainerMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link CurrentLdapEntityContainerSupplier}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CurrentLdapEntityContainerSupplierTest {

    private CurrentLdapEntityContainerSupplier supplier;

    @Mock
    private CurrentContainer currentContainer;

    @Mock
    private LdapEntityContainerMap containerMap;

    @Mock
    private Name name;

    @Mock
    private LdapEntityContainer container;

    @Mock
    private LdapService ldapService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        supplier = new CurrentLdapEntityContainerSupplier(currentContainer, containerMap, ldapService);
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        given(currentContainer.getDn()).willReturn(name);
        given(containerMap.getOrCreate(eq(name), any())).willReturn(container);
        //when
        val result = supplier.get();
        //then
        assertThat(result).isSameAs(container);
    }
}
