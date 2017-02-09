package net.kemitix.ldapmanager.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Features}.
 */
public class FeaturesTest {

    @Test
    public void valuesOfCoverage() {
        assertThat(Features.valueOf(Features.PASSWORD.toString())).isEqualTo(Features.PASSWORD);
        assertThat(Features.valueOf(Features.RENAME.toString())).isEqualTo(Features.RENAME);
    }
}
