package net.kemitix.ldapmanager.ldap;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;

/**
 * Test configuration for LDAP integration tests.
 *
 * @author Paul Campbell
 */
@Profile("ldap-test")
@Configuration
class LdapTestConfiguration {

    private LdapContextSource contextSource() {
        Integer serverPort = LdapConnectionIT.getServerPort();
        assert serverPort != null;
        val contextSource =
                new DefaultSpringSecurityContextSource("ldap://127.0.0.1:" + serverPort + "/dc=kemitix,dc=net");
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    BindAuthenticator authenticator() {
        final BindAuthenticator authenticator = new BindAuthenticator(contextSource());
        authenticator.setUserDnPatterns(new String[]{"uid={0}", "cn={0}"});
        return authenticator;
    }

    /**
     * LDAP Template.
     *
     * @return the LDAP Template
     */
    @Bean
    LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}

