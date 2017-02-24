package net.kemitix.ldapmanager.test;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Start and stop a test LDAP server.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@Slf4j
public final class TestLdapServer {

    public static final String ADMIN_PASSWORD = "adminspassword";

    public static final String ADMIN_USERNAME = "admin";

    public static final String BASE = "dc=kemitix,dc=net";

    private static final String LDIF_FILE = "server.ldif";

    private static InMemoryDirectoryServer server;

    private static Lock serverLock = new ReentrantLock();

    @NonNull
    @Getter
    private static Integer serverPort;

    @NonNull
    @Getter
    private static String ldapUrl;

    private TestLdapServer() {
    }

    /**
     * Start LDAP Server.
     *
     * @throws Exception if there is and error starting the server
     */
    public static void startServer() throws Exception {
        log.info("Starting LDAP Server");
        serverLock.lock();
        System.out.println("Lock acquired: Start LDAP Server...");
        if (server == null) {
            val config = new InMemoryDirectoryServerConfig(BASE);
            config.addAdditionalBindCredentials("cn=" + ADMIN_USERNAME, ADMIN_PASSWORD);
            server = new InMemoryDirectoryServer(config);
            server.importFromLDIF(true, findFile(LDIF_FILE));
        }
        server.startListening();
        serverPort = server.getListenPort();
        log.info("LDAP Port: {}", serverPort);
        ldapUrl = String.format("ldap://127.0.0.1:%d/%s", serverPort, TestLdapServer.BASE);
        log.info("Started LDAP Server: " + ldapUrl);
    }

    private static String findFile(final String file) throws IOException {
        final String filename = new ClassPathResource(file, TestLdapServer.class).getFile()
                                                                                 .getAbsolutePath();
        log.info("findFile({}): {}", file, filename);
        return filename;
    }

    /**
     * Stop LDAP Server.
     *
     * @throws Exception if there is and error stopping the server
     */
    public static void stopServer() throws Exception {
        serverPort = null;
        if (server != null) {
            server.shutDown(true);
        }
        System.out.println("Stopped LDAP Server.");
        serverLock.unlock();
        System.out.println("Lock released.");
    }
}
