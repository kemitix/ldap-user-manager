package net.kemitix.ldapmanager.ldap;

import net.kemitix.ldapmanager.domain.OU;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.DirContextAdapter;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OuContextMapper}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class OuContextMapperTest {

    @InjectMocks
    private OuContextMapper contextMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMapFromContext() throws Exception {
        //given
        DirContextAdapter ctx = new DirContextAdapter();
        final Name dn = LdapNameUtil.parse("dn=ou=container");
        final String name = "container";
        ctx.setDn(dn);
        ctx.setAttributeValue(LdapAttribute.OU, name);
        //when
        final OU ou = contextMapper.mapFromContext(ctx);
        //then
        assertThat(ou.getDn()).isEqualTo(dn);
        assertThat(ou.getOu()).isEqualTo(name);
    }
}
