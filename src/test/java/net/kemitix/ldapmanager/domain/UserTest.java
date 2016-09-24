package net.kemitix.ldapmanager.domain;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link User}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class UserTest {

    @Test
    public void shouldBuilder() throws Exception {
        //given
        val userToString = User.builder()
                               .cn("name")
                               .toString();
        //then
        Assertions.assertThat(userToString).contains("(cn=name)");
    }

    @Test
    public void shouldGetCn() throws Exception {
        //given
        val user = User.builder()
                       .cn("name")
                       .build();
        //then
        assertThat(user.getCn()).isEqualTo("name");
    }
}
