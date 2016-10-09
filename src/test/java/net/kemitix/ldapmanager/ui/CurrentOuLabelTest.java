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
        given(ldapOptions.getBase()).willReturn("dc=base");
        given(currentOuSupplier.get()).willReturn("");
        currentOuLabel = new CurrentOuLabel(ldapOptions, currentOuSupplier);
    }

    @Test
    public void updateShouldIncludeBaseWithoutLeadingCommaWhenInRoot() {
        //given
        given(currentOuSupplier.get()).willReturn("");
        //when
        currentOuLabel.onCurrentContainerChangerEventUpdateUiLabel();
        //then
        assertThat(currentOuLabel.getText()).isEqualTo("dc=base");
    }

    @Test
    public void updateShouldIncludeBaseWithCommaWhenNotInRoot() {
        //given
        given(currentOuSupplier.get()).willReturn("ou=users");
        //when
        currentOuLabel.onCurrentContainerChangerEventUpdateUiLabel();
        //then
        assertThat(currentOuLabel.getText()).isEqualTo("ou=users,dc=base");
    }
}
