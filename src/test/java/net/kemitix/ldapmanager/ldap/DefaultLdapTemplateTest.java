package net.kemitix.ldapmanager.ldap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.ContextSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultLdapTemplate}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLdapTemplateTest {

    @InjectMocks
    private DefaultLdapTemplate ldapTemplate;

    @Mock
    private ContextSource contextSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldContainContextSource() {
        assertThat(ldapTemplate.getContextSource()).isSameAs(contextSource);
    }
}
