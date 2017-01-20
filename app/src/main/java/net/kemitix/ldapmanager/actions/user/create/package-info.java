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

/**
 * Create/Insert a user.
 *
 * <ul> <li>{@link net.kemitix.ldapmanager.actions.user.create.CreateUserMenuItemFactory} - creates the {@link
 * net.kemitix.ldapmanager.actions.user.create.CreateUserMenuItem}</li> <li>{@link
 * net.kemitix.ldapmanager.actions.user.create.CreateUserMenuItem} - sends the {@link
 * net.kemitix.ldapmanager.events.CreateUserEvent}</li>
 * <li>{@link net.kemitix.ldapmanager.events.CreateUserEvent}
 * - displays the {@link net.kemitix.ldapmanager.actions.user.create.CreateUserFormPanel}</li> <li>{@link
 * net.kemitix.ldapmanager.actions.user.create.CreateUserFormPanel} - sends the {@link
 * net.kemitix.ldapmanager.events.CreateUserCommitEvent}</li> <li>{@link
 * net.kemitix.ldapmanager.events.CreateUserCommitEvent} - starts the {@link
 * net.kemitix.ldapmanager.actions.user.create.CreateUserAction}</li>
 * <li>{@link net.kemitix.ldapmanager.actions.user.create.CreateUserAction}
 * - creates the user</li> </ul>
 */

package net.kemitix.ldapmanager.actions.user.create;
