import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class MyHashMap<K, V> implements Map<K, V> {

    private int num_buckets = 1009;    //prime number
    private LinkedList<Element>[] buckets = new LinkedList[num_buckets];
    private int occupied = 0;

    public static void main(String[] args) {
        MyHashMap<Integer, Integer> m = new MyHashMap<>();
        m.put(1, 11);
        m.put(2, 22);
        m.put(3, 33);
        m.put(4, 44);
        m.put(5, 55);
        System.out.println(m.get(5));
        System.out.println(m.get(3));
        m.put(3, 15);
        System.out.println(m.get(3));
    }

    private int simpleHash(Object o) {
        return o.hashCode() % num_buckets;
    }

    @Override
    public int size() {
        return occupied;
    }

    @Override
    public boolean isEmpty() {
        return occupied == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public V get(Object key) {
        int key_hash = simpleHash(key);

        if (buckets[key_hash] == null) return null;
        for (Element<K, V> element : buckets[key_hash]) {
            if (element.getKey().equals(key)) {
                return element.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Element element = new Element();
        element.set(key, value);

        int key_hash = simpleHash(key);

        if (buckets[key_hash] == null) {
            buckets[key_hash] = new LinkedList<>();
            buckets[key_hash].add(element);
            return null;
        }

        for (Element e : buckets[key_hash]) {
            if (e.getKey().equals(key)) {
                e.setValue(value);
                return null;
            }
        }
        buckets[key_hash].add(element);
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    private class Element<K, V> {
        private K key;
        private V value;

        private K getKey() {
            return this.key;
        }

        private V getValue() {
            return this.value;
        }

        private void setValue(V value) {
            this.value = value;
        }

        private void set(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
