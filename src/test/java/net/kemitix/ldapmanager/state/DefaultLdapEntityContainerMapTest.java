package net.kemitix.ldapmanager.state;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.ldap.LdapName;
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

    private LdapName dn;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dn = LdapNameBuilder.newInstance("ou=users")
                            .build();
    }

    @Test
    public void getShouldReturnEmptyIfNotFound() {
        //when
        val result = containerMap.get(dn);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void getShouldReturnItemIfFound() {
        //given
        containerMap.put(dn, LdapEntityContainer.empty());
        //when
        val result = containerMap.get(dn);
        //then
        assertThat(result).isNotEmpty();
        result.ifPresent(item -> assertThat(item).isSameAs(LdapEntityContainer.empty()));
    }

    @Test
    public void getOrCreateShouldCreateWhenNotFound() throws Exception {
        //when
        val result = containerMap.getOrCreate(dn, key -> LdapEntityContainer.empty());
        //then
        assertThat(result).isSameAs(LdapEntityContainer.empty());
    }

    @Test
    public void getOrCreateShouldFindWhenPut() throws Exception {
        //given
        val container = LdapEntityContainer.of(new ArrayList<>());
        containerMap.put(dn, container);
        //when
        val result = containerMap.getOrCreate(dn, key -> LdapEntityContainer.empty());
        //then
        assertThat(result).isSameAs(container);
    }

    @Test
    public void clearShouldRemoveAll() throws Exception {
        //given
        containerMap.put(dn, LdapEntityContainer.empty());
        assertThat(containerMap.get(dn)).isNotEmpty();
        //when
        containerMap.clear();
        //then
        assertThat(containerMap.get(dn)).isEmpty();
    }
}
