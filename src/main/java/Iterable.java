/**
 * Abstract Collection in the Iterator pattern.
 */
public interface Iterable<T> {
    Iterator<T> getIterator();
}
