package net.kemitix.ldapmanager.ldap;

import lombok.Getter;
import lombok.val;
import net.kemitix.ldapmanager.domain.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.server.ApacheDSContainer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for LDAP.
 *
 * <p>Starts up an embedded LDAP Server before the tests are run, then shuts it down once they are all finished.</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LdapTestConfiguration.class, LdapConfiguration.class})
@ActiveProfiles("test")
public class LdapConnectionIT {

    private static final String BASE = "dc=kemitix,dc=net";

    private static ApacheDSContainer server;

    @Getter
    private static Integer serverPort;

    @Autowired
    private BindAuthenticator authenticator;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private ContextMapper<User> userContextMapper;

    @BeforeClass
    public static void startServer() throws Exception {
        server = new ApacheDSContainer(BASE, "classpath:net/kemitix/ldapmanager/ldap/users.ldif");
        int port = getAvailablePort();
        server.setPort(port);
        server.afterPropertiesSet();
        serverPort = port;
    }

    @AfterClass
    public static void stopServer() throws Exception {
        serverPort = null;
        if (server != null) {
            server.stop();
        }
    }

    private static int getAvailablePort() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            return serverSocket.getLocalPort();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Test
    public void canAuthenticateToLdap() {
        val admin = new UsernamePasswordAuthenticationToken("admin", "adminspassword");
        val userAdmin = authenticator.authenticate(admin);
        assertThat(userAdmin.getStringAttribute("cn")).isEqualTo("admin");
    }

    @Test
    public void ldapTemplateCanFindAUser() throws InvalidNameException {
        val query = LdapQueryBuilder.query()
                                    .filter("(cn=admin)");
        val users = ldapTemplate.search(LdapQueryBuilder.query()
                                                        .filter("(cn=bob)"), userContextMapper);
        assertThat(users).hasSize(1)
                         .extracting("cn")
                         .containsOnly("admin");
    }
}
