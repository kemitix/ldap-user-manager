package net.kemitix.ldapmanager.state;

import lombok.val;
import net.kemitix.ldapmanager.ldap.LdapOptions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.support.LdapNameBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DefaultCurrentContainer}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultCurrentContainerTest {

    @InjectMocks
    private DefaultCurrentContainer container;

    @Mock
    private LdapOptions ldapOptions;

    private String base = "ou=base";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        given(ldapOptions.getBase()).willReturn(base);
        container.init();
    }

    @Test
    public void shouldGetDn() throws Exception {
        assertThat(container.getDn()
                            .toString()).isEqualTo(base);
    }

    @Test
    public void shouldSetDn() throws Exception {
        //given
        val dn = LdapNameBuilder.newInstance("ou=users")
                                .build();
        //when
        container.setDn(dn);
        //then
        assertThat(container.getDn()
                            .toString()).isEqualTo("ou=users");
    }
}
