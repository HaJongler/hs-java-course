import java.util.Arrays;
import java.util.ArrayList;

/**
 * Concrete Collection in the Iterator pattern.
 */
public class LogList<T> implements Iterable<T> {
    private ArrayList<T> lst;

    @SafeVarargs
    public LogList(T... array) {
        this.lst = new ArrayList<>(Arrays.asList(array));
    }

    @Override
    public Iterator<T> getIterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public T next() {
                T element = lst.get(index);
                index++;
                return element;
            }

            @Override
            public boolean hasNext() {
                return index < lst.size();
            }
        };
    }

    public void addLog(T log) {
        this.lst.add(log);
    }
}
