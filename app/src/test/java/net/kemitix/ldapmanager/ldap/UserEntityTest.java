package net.kemitix.ldapmanager.ldap;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import org.junit.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link UserEntity}.
 */
public class UserEntityTest {

    @Test
    public void fromLdap() throws Exception {
        //given
        val dn = mock(Name.class);
        val cn = "cn";
        val sn = "sn";
        val subject = new UserEntity();
        subject.setDn(dn);
        subject.setCn(cn);
        subject.setSn(sn);
        //when
        val result = subject.fromLdap();
        //then
        assertThat(result).isInstanceOf(User.class);
        assertThat(result.getDn()).isSameAs(dn);
        assertThat(result.getCn()).isSameAs(cn);
        assertThat(result.getSn()).isSameAs(sn);
    }

    @Test
    public void from() throws Exception {
        //given
        val dn = mock(Name.class);
        val cn = "cn";
        val sn = "sn";
        val user = User.of(cn, sn, dn);
        //when
        val result = UserEntity.from(user);
        //then
        assertThat(result).isInstanceOf(UserEntity.class);
        val asUser = result.fromLdap();
        assertThat(asUser.getDn()).isSameAs(dn);
        assertThat(asUser.getCn()).isSameAs(cn);
        assertThat(asUser.getSn()).isSameAs(sn);
    }
}
