import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyHashMapTest extends TestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSizeBeforePut() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        assertEquals(0, map.size());
    }

    @Test
    public void testSizeAfterPut() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        assertNotEquals(0, map.size());
    }

    @Test
    public void testSizeAfterPutAndRemove() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        map.remove(5);
        assertEquals(0, map.size());
    }

    @Test
    public void testSizeAfterTwoPut() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        map.put(3, 3);
        assertEquals(2, map.size());
    }

    @Test
    public void testSizeAfterPutAndUpdate() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        map.put(5, 6);
        assertEquals(1, map.size());
    }

    @Test
    public void testIsEmptyNew() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        assertEquals(true, map.isEmpty());
    }

    @Test
    public void testIsEmptyAfterPut() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        assertEquals(false, map.isEmpty());
    }

    @Test
    public void testIsEmptyAfterPutAndRemove() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        map.remove(5);
        assertEquals(true, map.isEmpty());
    }

    @Test
    public void testContainsKeyExisting() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        assertEquals(true, map.containsKey(5));
    }

    @Test
    public void testContainsKeyNonExisting() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(3, 3);
        assertEquals(false, map.containsKey(2));
    }

    @Test
    public void testPutNullKey() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(null, 5);
        assertEquals(null, map.get(null));
    }

    @Test
    public void testPutNullValue() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, null);
        assertEquals(null, map.get(5));
    }

    @Test
    public void testGetInteger() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        assertEquals( (Integer) 5, map.get(5));
    }


    @Test
    public void testGetString() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("abc", "123");
        assertEquals("123", map.get("abc"));
    }

    @Test
    public void testGetStringInteger() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("xyz", 5);
        assertEquals( (Integer) 5, map.get("xyz"));
    }

    @Test
    public void testGetNonExisting() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        assertEquals(null, map.get(5));
    }

    @Test
    public void testGetAfterUpdate() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        map.put(5, 3);
        assertEquals((Integer) 3, map.get(5));
    }

    @Test
    public void testGetAfterRemove() {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        map.put(5, 5);
        map.put(3, 3);
        map.remove(5);
        assertEquals((Integer) null, map.get(5));
    }

    @Test
    public void testRemoveNonExisting() {
        MyHashMap<String, String> map = new MyHashMap<>();
        assertEquals(null, map.remove("a"));
    }

    @Test
    public void testRemoveExisting() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("a", "a");
        assertEquals("a", map.remove("a"));
    }

    @Test
    public void testClearSize() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        map.clear();
        assertEquals(true, map.isEmpty());
    }

    @Test
    public void testClearGet() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("a", "a");
        map.clear();
        assertEquals(null, map.get("a"));
    }
}