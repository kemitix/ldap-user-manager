package net.kemitix.ldapmanager.domain;

import lombok.val;
import org.junit.Test;

import javax.naming.Name;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LdapEntity}.
 */
public class LdapEntityTest {

    private Set<Features> featureSet;

    @Test
    public void hasFeature() throws Exception {
        //given
        featureSet = EnumSet.of(Features.RENAME);
        val subject = new TestLdapEntity();
        //when
        val resultTrue = subject.hasFeature(Features.RENAME);
        val resultFalse = subject.hasFeature(Features.PASSWORD);
        //then
        assertThat(resultTrue).isTrue();
        assertThat(resultFalse).isFalse();
    }

    private class TestLdapEntity implements LdapEntity {

        @Override
        public String name() {
            return null;
        }

        @Override
        public Name getDn() {
            return null;
        }

        @Override
        public Set<Features> getFeatureSet() {
            return featureSet;
        }
    }
}
