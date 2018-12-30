package org.eclipse.fx.ui.controls.tree;

import java.util.function.Predicate;

import javafx.scene.control.TreeItem;

/**
 * This interface can be used together with {@link FilterableTreeItem} to sort
 * the items in the list.
 *
 * @param <T>
 *            element type
 */
@FunctionalInterface
public interface TreeItemPredicate<T> {

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param parent
	 *            the parent tree item of the element or null if there is no
	 *            parent
	 * @param value
	 *            the value to be tested
	 * @return {@code true} if the input argument matches the
	 *         predicate,otherwise {@code false}
	 */
	boolean test(TreeItem<T> parent, T value);

	/**
	 * Utility method to create a TreeItemPredicate from a given
	 * {@link Predicate}
	 * 
	 * @param predicate
	 *            the predicate
	 * @return new TreeItemPredicate
	 */
	static <T> TreeItemPredicate<T> create(Predicate<T> predicate) {
		return (parent, value) -> predicate.test(value);
	}

}