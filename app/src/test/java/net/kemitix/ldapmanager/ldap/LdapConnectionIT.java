package net.kemitix.ldapmanager.ldap;

import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import net.kemitix.ldapmanager.test.TestLdapServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
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
public class LdapConnectionIT {

    @Autowired
    private BindAuthenticator authenticator;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private ContextMapper<User> userContextMapper;

    @BeforeClass
    public static void startServer() throws Exception {
        TestLdapServer.startServer();
    }

    @AfterClass
    public static void stopServer() throws Exception {
        TestLdapServer.stopServer();
    }

    @Test
    public void canAuthenticateToLdap() {
        val admin = new UsernamePasswordAuthenticationToken("admin", "adminspassword");
        val userAdmin = authenticator.authenticate(admin);
        assertThat(userAdmin.getStringAttribute("cn")).isEqualTo("admin");
    }

    @Test
    public void ldapTemplateCanFindAUser() throws InvalidNameException {
        //given
        val dn = LdapNameUtil.parse("cn=bob");
        val cn = "bob";
        val sn = "smith";
        val user = User.of(dn, cn, sn);
        ldapTemplate.create(UserEntity.from(user));
        //when
        val userFound = ldapTemplate.lookup(dn, userContextMapper);
        //then
        assertThat(userFound).as("found user")
                             .isNotNull()
                             .as("is equal")
                             .isEqualTo(user)
                             .as("property values set")
                             .hasFieldOrPropertyWithValue("dn", dn)
                             .hasFieldOrPropertyWithValue("cn", cn)
                             .hasFieldOrPropertyWithValue("sn", sn);
    }
}
