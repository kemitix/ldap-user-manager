package net.kemitix.ldapmanager.ldap;

import net.kemitix.ldapmanager.domain.OU;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.state.LdapEntityContainer;
import net.kemitix.ldapmanager.state.LogMessages;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Tests for {@link LdapService}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLdapServiceTest {

    private LdapService ldapService;

    @Mock
    private LdapTemplate ldapTemplate;

    @Mock
    private LogMessages logMessages;

    private Name dn;

    private OU ou;

    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ldapService = new DefaultLdapService(ldapTemplate, logMessages);
    }

    @Test
    public void shouldGetLdapEntityContainer() throws Exception {
        //given
        dn = LdapNameBuilder.newInstance().build();
        ou = OU.builder()
               .build();
        user = User.builder()
                   .build();
        given(ldapTemplate.find(anyObject(), eq(OU.class))).willReturn(Collections.singletonList(ou));
        given(ldapTemplate.find(anyObject(), eq(User.class))).willReturn(Collections.singletonList(user));
        //when
        final LdapEntityContainer result = ldapService.getLdapEntityContainer(dn);
        //then
        assertThat(result.getContents()).containsExactlyInAnyOrder(ou, user);
    }
}
