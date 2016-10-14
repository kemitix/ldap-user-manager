package net.kemitix.ldapmanager.navigation;

import lombok.Getter;
import lombok.NonNull;
import net.kemitix.ldapmanager.domain.OU;

/**
 * Raised when the switching context from one OU to another.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public final class NavigationItemOuActionEvent {

    @Getter
    private final OU ou;

    private NavigationItemOuActionEvent(final OU ou) {
        this.ou = ou;
    }

    /**
     * Create a new ApplicationEvent for when the current OU is switched to a another.
     *
     * @param ou The OU being switched to.
     *
     * @return the event
     */
    public static NavigationItemOuActionEvent of(@NonNull final OU ou) {
        return new NavigationItemOuActionEvent(ou);
    }
}
