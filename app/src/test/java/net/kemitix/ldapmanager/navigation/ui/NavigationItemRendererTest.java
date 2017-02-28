package net.kemitix.ldapmanager.navigation.ui;

import lombok.val;
import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link }.
 *
 * @author Paul Campbell (paul.campbell@hubio.com)
 */
public class NavigationItemRendererTest {

    @Test
    public void getLabel() throws Exception {
        //given
        val listBox = mock(DefaultNavigationItemActionListBox.class);
        val index = 1;
        val item = mock(NavigationItem.class);
        val label = "label";
        given(item.getLabel()).willReturn(label);
        val subject = new NavigationItemRenderer();
        //when
        val result = subject.getLabel(listBox, index, item);
        //then
        assertThat(result).isEqualTo(label);
    }
}
