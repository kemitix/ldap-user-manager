package net.kemitix.ldapmanager.ldap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link DefaultContextSource}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultContextSourceTest {

    @InjectMocks
    private DefaultContextSource contextSource;

    @Mock
    private LdapOptions ldapOptions;

    @Mock
    private LdapCredentials ldapCredentials;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInit() throws Exception {
        //given
        final String url = "url1";
        final String base = "dc=base";
        final String userDn = "cn=user-dn,dc=base";
        final String password = "password";
        given(ldapOptions.getUrls()).willReturn(new String[]{url});
        given(ldapOptions.getBase()).willReturn(base);
        given(ldapOptions.getUserDn()).willReturn(userDn);
        given(ldapCredentials.getPassword()).willReturn(password);
        //when
        contextSource.init();
        //then
        assertThat(contextSource.getUrls()).containsExactly(url);
        assertThat(contextSource.getBaseLdapName()
                                .toString()).isEqualTo(base);
        assertThat(contextSource.getUserDn()).isEqualTo(userDn);
        assertThat(contextSource.getPassword()).isEqualTo(password);
    }

}
