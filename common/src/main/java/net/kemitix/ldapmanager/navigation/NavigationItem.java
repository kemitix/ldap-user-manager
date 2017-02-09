/*
The MIT License (MIT)

Copyright (c) 2016 Paul Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.kemitix.ldapmanager.navigation;

import net.kemitix.ldapmanager.domain.FeatureSet;

import javax.naming.Name;
import java.util.Locale;

/**
 * Item that can appear on the Navigation panel.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface NavigationItem extends Runnable, Comparable<NavigationItem>, FeatureSet {

    /**
     * Return the DN of the item.
     *
     * @return The DN of the item.
     */
    Name getDn();

    /**
     * Return the name of the item.
     *
     * @return the item's name
     */
    String getName();

    /**
     * Publish an event indicating that this item has been selected.
     */
    void publishAsSelected();

    /**
     * Returns the name of the NavigationItem suitable for sorting.
     *
     * <p>Names should be all lowercase and include a 'type' prefix to allow all items of a type to be grouped
     * together. e.g. 'user-bob', 'ou-users'</p>
     *
     * @return the sortable name
     */
    default String getSortableName() {
        return String.format("%s-%s", getSortableType(), getName().toLowerCase(Locale.ENGLISH));
    }

    /**
     * Returns the type of navigation item as a sortable string.
     *
     * @return The type of navigation item.
     */
    String getSortableType();

    /**
     * Compare two NavigationItems using their {@link #getSortableName()}.
     *
     * @param other The other NavigationItem to compare with
     *
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    default int compareTo(NavigationItem other) {
        return getSortableName().compareTo(other.getSortableName());
    }

    /**
     * Publish an event requesting that the user wants to rename this item.
     */
    void publishRenameRequest();

    /**
     * Publish an event requesting that the user wants to change the password of this item.
     *
     * <p>If the child class does not support passwords, then it can simply return after doing nothing.</p>
     */
    void publishChangePasswordRequest();
}
