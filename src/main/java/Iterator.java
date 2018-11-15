/**
 * Traversal abstraction in the Iterator pattern.
 */
public interface Iterator<T> {
    T next();
    boolean hasNext();
}
