package net.kemitix.ldapmanager.state;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.support.LdapNameBuilder;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultLdapEntityContainerMap}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class DefaultLdapEntityContainerMapTest {

    @InjectMocks
    private DefaultLdapEntityContainerMap containerMap;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindOrDefaultWhenNotPut() throws Exception {
        //given
        val name = LdapNameBuilder.newInstance("ou=users")
                                  .build();
        //when
        val result = containerMap.getOrCreate(name, key -> LdapEntityContainer.empty());
        //then
        assertThat(result).isSameAs(LdapEntityContainer.empty());
    }

    @Test
    public void shouldFindOfDefaultWhenPut() throws Exception {
        //given
        val name = LdapNameBuilder.newInstance("ou=users")
                                  .build();
        val container = LdapEntityContainer.of(new ArrayList<>());
        containerMap.put(name, container);
        //when
        val result = containerMap.getOrCreate(name, key -> LdapEntityContainer.empty());
        //then
        assertThat(result).isSameAs(container);
    }
}
