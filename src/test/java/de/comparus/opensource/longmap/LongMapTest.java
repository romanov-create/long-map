package de.comparus.opensource.longmap;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LongMapTest {
    @Test
    public void testPutAndGetWithUniqueKeys() {
        LongMap<String> longMap = new LongMapImpl<>();

        assertNull(longMap.put(1L, "One"));
        assertNull(longMap.put(2L, "Two"));

        assertEquals("One", longMap.get(1L));
        assertEquals("Two", longMap.get(2L));
    }

    @Test
    public void testPutAndGetWithDuplicateKeys() {
        LongMap<String> longMap = new LongMapImpl<>();

        assertNull(longMap.put(1L, "One"));
        //return previous value
        assertEquals("One", longMap.put(1L, "Two"));
        assertEquals("Two", longMap.get(1L));
    }

    @Test
    public void testGetEntryWithNotExistKey() {
        LongMap<String> longMap = new LongMapImpl<>();
        longMap.put(1L, "One");

        assertNull(longMap.get(2L));
    }

    @Test
    public void testRemoveEntry() {
        LongMap<String> longMap = new LongMapImpl<>();
        longMap.put(1L, "One");

        assertEquals("One", longMap.remove(1L));
        assertNull(longMap.get(1L));
    }

    @Test
    public void testIsEmpty() {
        LongMap<String> longMap = new LongMapImpl<>();
        assertEquals(0, longMap.size());
        assertTrue(longMap.isEmpty());

        longMap.put(1L, "One");
        assertFalse(longMap.isEmpty());
    }

    @Test
    public void testContainsKey() {
        LongMap<String> longMap = new LongMapImpl<>();
        longMap.put(1L, "One");

        assertTrue(longMap.containsKey(1L));
        assertFalse(longMap.containsKey(2L));
    }

    @Test
    public void testContainsValue() {
        LongMap<String> longMap = new LongMapImpl<>();
        longMap.put(1L, "One");

        assertTrue(longMap.containsValue("One"));
        assertFalse(longMap.containsValue("Two"));
    }

    @Test
    public void testGetKeys() {
        LongMap<String> longMap = new LongMapImpl<>();

        longMap.put(1L, "One");
        longMap.put(2L, "Two");

        assertArrayEquals(new long[]{1L, 2L}, longMap.keys());
    }

    @Test
    public void testGetValues() {
        LongMap<String> longMap = new LongMapImpl<>();

        longMap.put(1L, "One");
        longMap.put(2L, "Two");

        assertArrayEquals(new String[]{"One", "Two"}, longMap.values());
    }

    @Test
    public void testSize() {
        LongMap<String> longMap = new LongMapImpl<>();

        longMap.put(1L, "One");
        longMap.put(2L, "Two");

        assertEquals(2, longMap.size());
    }

    @Test
    public void testClear() {
        LongMap<String> longMap = new LongMapImpl<>();

        longMap.put(1L, "One");
        longMap.put(2L, "Two");

        longMap.clear();

        assertEquals(0, longMap.size());
        assertTrue(longMap.isEmpty());
    }
}