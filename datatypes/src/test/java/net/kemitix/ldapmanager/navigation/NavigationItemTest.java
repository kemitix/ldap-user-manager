package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
import net.kemitix.ldapmanager.ldap.LdapNameUtil;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Name;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NavigationItem}.
 */
public class NavigationItemTest {

    private NavigationItem navigationItem;

    private String sortableType;

    private String name;

    @Before
    public void setUp() throws Exception {
        sortableType = "TYPE";
        name = "cn=NAME";
        navigationItem = new TestNavigationItem();
    }

    @Test
    public void getSortableName() throws Exception {
        assertThat(navigationItem.getSortableName()).contains(sortableType)
                                                    .containsIgnoringCase(name);
    }

    @Test
    public void compareTo() throws Exception {
        //given
        val other = new TestNavigationItem();
        //then
        assertThat(navigationItem.compareTo(other)).isEqualTo(0);
    }

    @Test
    public void getLabel() throws Exception {
        assertThat(navigationItem.getLabel()).isEqualTo(name);
    }

    private class TestNavigationItem implements NavigationItem {

        @Override
        public Name getDn() {
            return LdapNameUtil.parse(name);
        }

        @Override
        public void publishAsSelected() {

        }

        @Override
        public String getSortableType() {
            return sortableType;
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
