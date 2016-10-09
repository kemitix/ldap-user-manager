package net.kemitix.ldapmanager.suppliers;

import net.kemitix.ldapmanager.state.CurrentContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link CurrentOuSupplier}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CurrentOuSupplierTest {

    private CurrentOuSupplier currentOuSupplier;

    @Mock
    private CurrentContainer currentContainer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        currentOuSupplier = new CurrentOuSupplier(currentContainer);
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        given(currentContainer.getDn()).willReturn(LdapNameBuilder.newInstance("ou=container")
                                                                  .build());
        //when
        final String result = currentOuSupplier.get();
        //then
        assertThat(result).isEqualTo("ou=container");
    }
}
