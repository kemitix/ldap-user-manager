package net.kemitix.ldapmanager.navigation;

import lombok.val;
import net.kemitix.ldapmanager.domain.Features;
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
        name = "NAME";
        navigationItem = new NavigationItem() {
            @Override
            public Name getDn() {
                return null;
            }

            @Override
            public String getName() {
                return name;
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
        };
    }

    @Test
    public void getSortableName() throws Exception {
        assertThat(navigationItem.getSortableName()).contains(sortableType)
                                                    .containsIgnoringCase(name);
    }

    @Test
    public void compareTo() throws Exception {
        //given
        val other = new NavigationItem() {
            @Override
            public boolean hasFeature(final Features feature) {
                return false;
            }

            @Override
            public void run() {

            }

            @Override
            public Name getDn() {
                return null;
            }

            @Override
            public String getName() {
                return "other";
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
        };
        //then
        assertThat(navigationItem.compareTo(other)).isLessThan(0);
    }
}
