package net.kemitix.ldapmanager.context;

import net.kemitix.ldapmanager.navigation.NavigationItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

/**
 * Tests for {@link RenameMenuItemFactory}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public class RenameMenuItemFactoryTest {

    private RenameMenuItemFactory menuItemFactory;

    @Mock
    private NavigationItem navigationItem;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuItemFactory = new RenameMenuItemFactory();
    }

    @Test
    public void labelisRename() throws Exception {
        //when
        final List<MenuItem> menuItems = menuItemFactory.create(navigationItem)
                                                        .collect(Collectors.toList());
        //then
        assertThat(menuItems).hasSize(1);
        assertThat(menuItems.get(0)
                            .getLabel()).isEqualTo("Rename");
    }

    @Test
    public void runActionShouldPublishRenameRequest() throws Exception {
        //when
        final List<MenuItem> menuItems = menuItemFactory.create(navigationItem)
                                                        .collect(Collectors.toList());
        //then
        assertThat(menuItems).hasSize(1);
        menuItems.get(0)
                 .getAction()
                 .run();
        then(navigationItem).should()
                            .publishRenameRequest();
    }
}
