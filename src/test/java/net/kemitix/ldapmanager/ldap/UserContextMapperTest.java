package net.kemitix.ldapmanager.ldap;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.DirContextAdapter;

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
    public void canMapShouldReturnTrueWhenCanMap() {
        //given
        val ctx = new DirContextAdapter();
        ctx.setAttributeValue(LdapAttribute.CN, "common name");
        ctx.setAttributeValue(LdapAttribute.SN, "surname");
        //then
        assertThat(contextMapper.canMapContext(ctx)).isTrue();
    }

    @Test
    public void canMapShouldReturnFalseWhenCnMissing() {
        //given
        val ctx = new DirContextAdapter();
        ctx.setAttributeValue(LdapAttribute.SN, "surname");
        //then
        assertThat(contextMapper.canMapContext(ctx)).isFalse();
    }

    @Test
    public void canMapShouldReturnFalseWhenSnMissing() {
        //given
        val ctx = new DirContextAdapter();
        ctx.setAttributeValue(LdapAttribute.CN, "common name");
        //then
        assertThat(contextMapper.canMapContext(ctx)).isFalse();
    }

    @Test
    public void shouldMapFromContext() throws Exception {
        //given
        val ctx = new DirContextAdapter();
        val dn = LdapNameUtil.parse("cn=name");
        ctx.setDn(dn);
        ctx.setAttributeValue(LdapAttribute.CN, "common name");
        ctx.setAttributeValue(LdapAttribute.SN, "surname");
        //when
        val user = contextMapper.mapFromContext(ctx);
        //then
        assertThat(user.getDn()).isEqualTo(dn);
        assertThat(user).isInstanceOf(User.class);
        assertThat(user.getCn()).isEqualTo("common name");
        assertThat(user.getSn()).isEqualTo("surname");
    }
}
