/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.ldapmanager.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import lombok.Getter;
import lombok.val;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Abstract class to provide an running instance of an LDAP server for integration tests.
 *
 * <p>To use this include the annotation: {@code @ActiveProfiles({"test", "ldap-connection-it"})}</p>
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class AbstractLdapConnectionIntegrationTest {

    private static final String BASE = "dc=kemitix,dc=net";

    private static InMemoryDirectoryServer server;

    private static Lock serverLock = new ReentrantLock();

    @Getter
    private static Integer serverPort;

    @BeforeClass
    public static void startServer() throws Exception {
        System.out.println("About to Start LDAP Server...");
        serverLock.lock();
        System.out.println("Lock acquired: Start LDAP Server...");
        if (server == null) {
            val config = new InMemoryDirectoryServerConfig(BASE);
            config.addAdditionalBindCredentials("cn=Directory Manager", "password");
            server = new InMemoryDirectoryServer(config);
            server.importFromLDIF(true, "src/test/resources/net/kemitix/ldapmanager/ldap/users.ldif");
        }
        server.startListening();
        serverPort = server.getListenPort();
        System.out.println("serverPort = " + serverPort);
    }

    @AfterClass
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
