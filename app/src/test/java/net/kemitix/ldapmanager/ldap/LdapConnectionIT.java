package net.kemitix.ldapmanager.ldap;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.InvalidNameException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for LDAP.
 *
 * <p>Starts up an embedded LDAP Server before the tests are run, then shuts it down once they are all finished.</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "ldap-connection-it"})
public class LdapConnectionIT extends AbstractLdapConnectionIntegrationTest {

    @Autowired
    private BindAuthenticator authenticator;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private ContextMapper<User> userContextMapper;

    @Test
    public void canAuthenticateToLdap() {
        val admin = new UsernamePasswordAuthenticationToken("admin", "adminspassword");
        val userAdmin = authenticator.authenticate(admin);
        assertThat(userAdmin.getStringAttribute("cn")).isEqualTo("admin");
    }

    @Test
    public void ldapTemplateCanFindAUser() throws InvalidNameException {
        //given
        ldapTemplate.create(User.builder()
                                .dn(LdapNameUtil.parse("cn=bob"))
                                .cn("bob")
                                .sn("smith")
                                .build());
        //when
        val users = ldapTemplate.search(LdapQueryBuilder.query()
                                                        .filter("(cn=bob)"), userContextMapper);
        //then
        assertThat(users).hasSize(1)
                         .extracting("cn")
                         .containsOnly("bob");
    }
}
