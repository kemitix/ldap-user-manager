package net.kemitix.ldapmanager.navigation;

import lombok.Getter;
import net.kemitix.ldapmanager.domain.User;

/**
 * Raised when the user presses Enter or Space on a {@link UserNavigationItem}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class NavigationItemUserActionEvent {

    @Getter
    private final User user;

    private NavigationItemUserActionEvent(final User user) {
        this.user = user;
    }

    /**
     * Create a new ApplicationEvent for when the User is opened.
     *
     * @param user The user
     *
     * @return the event
     */
    public static NavigationItemUserActionEvent of(final User user) {
        return new NavigationItemUserActionEvent(user);
    }
}
