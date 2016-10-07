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

package net.kemitix.ldapmanager.state;

import javax.naming.Name;
import java.util.Optional;
import java.util.function.Function;

/**
 * Interface for mapping a {@link Name} to an {@link LdapEntityContainer}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
public interface LdapEntityContainerMap {

    /**
     * Search for an LdapEntity container for the dn, returning in an Optional.
     *
     * @param dn The container's DN attribute
     *
     * @return an optional containing the container, or empty if none found.
     */
    Optional<LdapEntityContainer> get(Name dn);

    /**
     * Searches for an LdapEntity container for the dn, returning it, or else the default value.
     *
     * @param key             The name of the LdapEntity container
     * @param mappingFunction The function to create the value if one doesn't already exist
     *
     * @return the existing or newly created container
     */
    LdapEntityContainer getOrCreate(Name key, Function<Name, LdapEntityContainer> mappingFunction);

    /**
     * Places the LdapEntity container into the map for the given key, discarding any previous value.
     *
     * @param key      The name of the LdapEntity container
     * @param newValue The value to place in the map
     */
    void put(Name key, LdapEntityContainer newValue);

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     */
    void clear();
}
