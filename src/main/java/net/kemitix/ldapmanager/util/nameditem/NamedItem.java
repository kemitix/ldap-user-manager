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

package net.kemitix.ldapmanager.util.nameditem;

import net.kemitix.ldapmanager.navigation.NavigationItem;

/**
 * A tuple of a name and an item.
 *
 * @param <T> the type of the item
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 * @deprecated Only used with {@link NavigationItem} which now has a {@link NavigationItem#getName()}.
 */
@Deprecated
public interface NamedItem<T> {

    /**
     * Creates a new {@code NamedItem}.
     *
     * @param name The name of the item
     * @param item The item
     * @param <T>  The type of the item
     *
     * @return the named item
     */
    static <T> NamedItem<T> of(String name, T item) {
        return new DefaultNamedItem<T>(name, item);
    }

    /**
     * Gets the item's name.
     *
     * @return the name of the item
     */
    String getName();

    /**
     * Gets the item.
     *
     * @return the item
     */
    T getItem();
}
