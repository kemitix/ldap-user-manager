package net.kemitix.ldapmanager.ldap;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.DirContextAdapter;

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
    public void canMapShouldReturnTrueWhenCanMap() {
        //given
        val ctx = new DirContextAdapter();
        ctx.setAttributeValue(LdapAttribute.OU, "container");
        //then
        assertThat(contextMapper.canMapContext(ctx)).isTrue();
    }

    @Test
    public void canMapShouldReturnFalseWhenOuMissing() {
        //given
        val ctx = new DirContextAdapter();
        //then
        assertThat(contextMapper.canMapContext(ctx)).isFalse();
    }

    @Test
    public void shouldMapFromContext() throws Exception {
        //given
        val ctx = new DirContextAdapter();
        val dn = LdapNameUtil.parse("dn=ou=container");
        ctx.setDn(dn);
        ctx.setAttributeValue(LdapAttribute.OU, "container");
        //when
        val ou = contextMapper.mapFromContext(ctx);
        //then
        assertThat(ou.getDn()).isEqualTo(dn);
        assertThat(ou.getOu()).isEqualTo("container");
    }
}
