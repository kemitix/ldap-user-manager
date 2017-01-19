package net.kemitix.ldapmanager.popupmenus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MenuItemType}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class MenuItemTypeTest {

    @Test
    public void shouldGetValueOf() {
        assertThat(MenuItemType.valueOf("CREATE")).isEqualTo(MenuItemType.CREATE);
        assertThat(MenuItemType.valueOf("MODIFY")).isEqualTo(MenuItemType.MODIFY);
    }
}
