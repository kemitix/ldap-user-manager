package net.kemitix.ldapmanager.ui;

import net.kemitix.ldapmanager.ldap.LdapOptions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link CurrentOuLabel}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class CurrentOuLabelTest {

    private CurrentOuLabel currentOuLabel;

    @Mock
    private LdapOptions ldapOptions;

    @Mock
    private Supplier<String> currentOuSupplier;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        given(currentOuSupplier.get()).willReturn("ou=base");
        currentOuLabel = new CurrentOuLabel(currentOuSupplier);
    }

    @Test
    public void shouldSetLabelFromLdapOptions() {
        assertThat(currentOuLabel.getText()).isEqualTo("ou=base");
    }

    @Test
    public void shouldBeAbleToChangeLabel() {
        //when
        currentOuLabel.setText("new ou");
        //then
        assertThat(currentOuLabel.getText()).isEqualTo("new ou");
    }
}
