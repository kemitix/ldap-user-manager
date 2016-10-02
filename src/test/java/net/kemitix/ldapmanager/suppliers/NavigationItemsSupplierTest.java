package net.kemitix.ldapmanager.suppliers;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link NavigationItemsSupplier}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NavigationItemsSupplierTest {

    @InjectMocks
    private NavigationItemsSupplier supplier;

    @Mock
    private Supplier<LdapEntityContainer> currentLdapContainerSupplier;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGet() throws Exception {
        //given
        given(currentLdapContainerSupplier.get()).willReturn(LdapEntityContainer.of(Collections.singletonList(
                User.builder()
                    .cn("user name")
                    .build())));
        //when
        val result = supplier.get();
        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("user name");
    }
}
