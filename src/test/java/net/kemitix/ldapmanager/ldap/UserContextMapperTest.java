package net.kemitix.ldapmanager.ldap;

import net.kemitix.ldapmanager.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UserContextMapper}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class UserContextMapperTest {

    @InjectMocks
    private UserContextMapper contextMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMapFromContext() throws Exception {
        //given
        DirContextAdapter ctx = new DirContextAdapter();
        final Name dn = LdapNameBuilder.newInstance("dn=cn=name")
                                       .build();
        final String cn = "common name";
        final String sn = "surname";
        ctx.setDn(dn);
        ctx.setAttributeValue(LdapAttribute.CN, cn);
        ctx.setAttributeValue(LdapAttribute.SN, sn);
        //when
        final User user = contextMapper.mapFromContext(ctx);
        //then
        assertThat(user.getDn()).isEqualTo(dn);
        assertThat(user.getCn()).isEqualTo(cn);
        assertThat(user.getSn()).isEqualTo(sn);
    }
}
