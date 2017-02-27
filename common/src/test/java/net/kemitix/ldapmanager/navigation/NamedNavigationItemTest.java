package net.kemitix.ldapmanager.navigation;

import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
import org.junit.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NamedNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class NamedNavigationItemTest {

    @Test
    public void shouldGetName() throws Exception {
        //given
        val expected = "item name";
        val subject = new TestNamedNavigationItem(expected);
        //when
        val result = subject.getName();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldToString() throws Exception {
        //given
        val expected = "item name";
        val subject = new TestNamedNavigationItem(expected);
        //when
        val result = subject.toString();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @RequiredArgsConstructor
    private class TestNamedNavigationItem extends NamedNavigationItem {

        private final String itemName;

        @Override
        public Name getDn() {
            return null;
        }

        @Override
        public String getName() {
            return itemName;
        }

        @Override
        public void publishAsSelected() {

        }

        @Override
        public String getSortableType() {
            return null;
        }

        @Override
        public void publishRenameRequest() {

        }

        @Override
        public void publishChangePasswordRequest() {

        }

        @Override
        public void run() {

        }

        @Override
        public boolean hasFeature(final Features feature) {
            return false;
        }
    }
}
