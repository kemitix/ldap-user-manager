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

package net.kemitix.ldapmanager.ui;

import com.googlecode.lanterna.gui2.AbstractListBox;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import lombok.val;

/**
 * An AbstractListBox with overridable methods to intercept default behaviour.
 *
 * @param <T> Should always be itself, see {@code AbstractComponent}
 * @param <V> Type of items this list box contains
 *
 * @author Paul Campbell (pcampbell@kemitix.net)
 */
@SuppressWarnings({"ClassWithoutLogger", "PublicMethodWithoutLogging", "PublicMethodNotExposedInInterface"})
public abstract class AbstractListenableListBox<V, T extends AbstractListBox<V, T>> extends AbstractListBox<V, T> {

    /**
     * Constructor.
     */
    protected AbstractListenableListBox() {
        super(null);
    }

    @Override
    public final synchronized Interactable.Result handleKeyStroke(final KeyStroke keyStroke) {
        val watcher = AbstractListenableListBox.SelectionWatcher.create(this);

        if (onHandleKeyStroke(keyStroke) == Interactable.Result.HANDLED) {
            return Interactable.Result.HANDLED;
        }
        final Interactable.Result result = super.handleKeyStroke(keyStroke);

        watcher.update();

        return result;
    }

    @Override
    protected final synchronized void afterEnterFocus(
            final Interactable.FocusChangeDirection direction, final Interactable previouslyInFocus
                                                     ) {
        val watcher = AbstractListenableListBox.SelectionWatcher.create(this);

        super.afterEnterFocus(direction, previouslyInFocus);

        watcher.update();
    }

    @Override
    public final synchronized T addItem(final V item) {
        val watcher = AbstractListenableListBox.SelectionWatcher.create(this);

        final T result = super.addItem(item);

        watcher.update();

        return result;
    }

    @Override
    public final synchronized T clearItems() {
        val watcher = AbstractListenableListBox.SelectionWatcher.create(this);

        final T result = super.clearItems();

        watcher.update();

        return result;
    }

    @Override
    public final synchronized T setSelectedIndex(final int index) {
        val watcher = AbstractListenableListBox.SelectionWatcher.create(this);

        final T result = super.setSelectedIndex(index);

        watcher.update();

        return result;
    }

    /**
     * Implementations should override this method to be notified when a Key Stroke may be processed.
     *
     * <p>If the keystroke is handled then the overriding method should return {@link Interactable.Result#HANDLED},
     * otherwise it should return {@link Interactable.Result#UNHANDLED} to pass key keystroke on the the Lnaterna UI for
     * handling.</p>
     *
     * @param keyStroke The key stroke to be handled
     *
     * @return {@link Interactable.Result#HANDLED} if handled, otherwise {@link Interactable.Result#UNHANDLED}, not null
     */
    @SuppressWarnings({"MethodMayBeStatic", "WeakerAccess", "DesignForExtension"})
    public Interactable.Result onHandleKeyStroke(final KeyStroke keyStroke) {
        return Interactable.Result.UNHANDLED;
    }

    /**
     * Receive notifications when the selected item in the list box changes.
     *
     * <p>This implementation does nothing.</p>
     *
     * <p>Override this method to receive these notifications.</p>
     *
     * <p>Note that indexes of -1 indicate no selection. Also beware that an index may be 0 even while the list is
     * empty. This is probably a bug.</p>
     *
     * @param oldSelectionIndex The index of the previously selected item.
     * @param newSelectionIndex The index of the newly selected item.
     */
    @SuppressWarnings("NoopMethodInAbstractClass")
    public void onSelectionChange(final int oldSelectionIndex, final int newSelectionIndex) {
        // do nothing
    }

    /**
     * Records the selected index when created, then upon update, if the selected index has changed call the {@link
     * AbstractListenableListBox#onSelectionChange(int, int)} method.
     *
     * @param <T> Should always be the type of the listbox , see {@code AbstractComponent}
     * @param <V> Type of items the list box contains
     */
    private static final class SelectionWatcher<V, T extends AbstractListBox<V, T>> {

        private final int oldSelectedIndex;

        private final AbstractListenableListBox<V, T> listBox;

        private SelectionWatcher(final AbstractListenableListBox<V, T> listBox) {
            this.listBox = listBox;
            this.oldSelectedIndex = listBox.getSelectedIndex();
        }

        @SuppressWarnings("MethodReturnOfConcreteClass")
        public static <V, T extends AbstractListBox<V, T>> AbstractListenableListBox.SelectionWatcher<V, T> create(
                final AbstractListenableListBox<V, T> listBox
                                                                                                                  ) {
            return new AbstractListenableListBox.SelectionWatcher<V, T>(listBox);
        }

        void update() {
            final int newSelectedIndex = listBox.getSelectedIndex();
            if (oldSelectedIndex != newSelectedIndex) {
                listBox.onSelectionChange(oldSelectedIndex, newSelectedIndex);
            }
        }
    }
}
