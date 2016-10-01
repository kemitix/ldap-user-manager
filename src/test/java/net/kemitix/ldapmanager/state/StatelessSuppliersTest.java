package net.kemitix.ldapmanager.state;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StatelessSuppliers}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class StatelessSuppliersTest {

    @InjectMocks
    private StatelessSuppliers suppliers;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldTimeSupplier() throws Exception {
        assertThat(suppliers.timeSupplier()
                            .get()).matches("^\\d\\d:\\d\\d:\\d\\d$");
    }
}
