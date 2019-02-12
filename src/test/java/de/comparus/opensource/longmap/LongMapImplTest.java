package de.comparus.opensource.longmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.Objects.isNull;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LongMapImplTest {
    private static final int START = 0;
    private static final int FINISH = 50000;
    Map<Long, String> map = new HashMap<>();
    LongMapImpl<String> map2 = new LongMapImpl<>();

    @Test
    public void putHashMap() {
        System.out.println("start");
        long start = System.currentTimeMillis();
        fillTheHashMap();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        System.out.println("map.size() = " + map.size());
    }

    @Test
    public void putMyMap() {
        System.out.println("start");
        long start = System.currentTimeMillis();
        fillMyMap();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        System.out.println("map2.size() = " + map2.size());
    }

    @Test
    public void getFromHashMap() {
        fillTheHashMap();
        System.out.println("start");
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> {
            String obj = map.get(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from getFromHashMap");
        });
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void getFromMyMap() {
        fillMyMap();
        System.out.println("map2.size() start = " + map2.size());
        System.out.println("start");
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> {
            String obj = map2.get(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from getFromMyMap");
        });
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        System.out.println("map2.size() = " + map2.size());
    }

    @Test
    public void removeFromHashMap() {
        fillTheHashMap();
        int sizeBefore = map.size();
        assertEquals(FINISH, sizeBefore);

        System.out.println("map.size() start = " + sizeBefore);
        System.out.println("start");
        List<Long> range = LongStream.range(START, FINISH).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        long start = System.currentTimeMillis();
        range.forEach(e -> {
            String obj = map.remove(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from removeFromHashMap");
        });
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        int sizeAfter = map.size();
        System.out.println("map.size() finish = " + sizeAfter);
        assertEquals(0, sizeAfter);

    }

    //this method works fine only because numbers inside my map are sorted.
    @Test
    public void removeFromMyMap() {
        fillMyMap();
        long sizeBefore = map2.size();
        assertEquals(FINISH, sizeBefore);
        System.out.println("map2.size() start = " + map2.size());
        System.out.println("start");
        List<Long> range = LongStream.range(START, FINISH).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        long start = System.currentTimeMillis();
        range.forEach(e -> {
            String obj = map2.remove(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from removeFromMyMap");
        });
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        System.out.println("map2.size() finish = " + map2.size());
        long sizeAfter = map2.size();
        System.out.println("map2.size() finish = " + sizeAfter);
        assertEquals(0, sizeAfter);
    }

    @Test
    public void isEmpty() {
        assertTrue(map2.isEmpty());
        map2.put(1L, "test");
        assertFalse(map2.isEmpty());
        map2.remove(1L);
        assertTrue(map2.isEmpty());
    }

    @Test
    public void containsKeyHashMap() {
        fillTheHashMap();
        System.out.println("start");
        Random random = new Random();
        long key = (long) random.nextInt(FINISH);
        long start = System.currentTimeMillis();
        boolean containsKey = map.containsKey(key);
        assertTrue(containsKey);
        containsKey = map.containsKey((long) FINISH + 1);
        assertFalse(containsKey);
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void containsKeyMyMap() {
        fillMyMap();
        System.out.println("start");
        Random random = new Random();
        long key = (long) random.nextInt(FINISH);
        long start = System.currentTimeMillis();
        boolean containsKey = map2.containsKey(key);
        assertTrue(containsKey);
        containsKey = map2.containsKey((long) FINISH + 1);
        assertFalse(containsKey);
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void containsValueHashMap() {
        fillTheHashMap();
        System.out.println("start");
        Random random = new Random();
        long key = (long) random.nextInt(FINISH);
        long start = System.currentTimeMillis();
        boolean containsValue = map.containsValue("one" + key);
        assertTrue(containsValue);
        containsValue = map.containsValue("one" + FINISH);
        assertFalse(containsValue);
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void containsValueMyMap() {
        fillMyMap();
        System.out.println("start");
        Random random = new Random();
        long key = (long) random.nextInt(FINISH);
        long start = System.currentTimeMillis();
        boolean containsValue = map2.containsValue("one" + key);
        assertTrue(containsValue);
        containsValue = map2.containsValue("one" + FINISH);
        assertFalse(containsValue);
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void keysHashMap() {
        fillTheHashMap();
        long start = System.currentTimeMillis();
        Set<Long> keys = map.keySet();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        assertEquals(FINISH, keys.size());
    }

    @Test
    public void keysMyMap() {
        fillMyMap();
        long start = System.currentTimeMillis();
        long[] keys = map2.keys();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        assertEquals(FINISH, keys.length);
    }

    @Test
    public void valuesHashMap() {
        fillTheHashMap();
        long start = System.currentTimeMillis();
        Collection<String> values = map.values();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        assertEquals(FINISH, values.size());
    }

    @Test
    public void valuesMyMap() {
        fillMyMap();
        long start = System.currentTimeMillis();
        String[] values = map2.values();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
        assertEquals(FINISH, values.length);
    }

    @Test
    public void size() {
        fillMyMap();
        System.out.println("start");
        long start = System.currentTimeMillis();

        long size = map2.size();
        assertEquals(FINISH, size);

        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void clear() {
        fillMyMap();
        System.out.println("start");
        long start = System.currentTimeMillis();

        map2.clear();

        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    private void fillTheHashMap() {
        LongStream.range(START, FINISH).forEach(e -> map.put(e, "one" + e));
    }

    private void fillMyMap() {
        LongStream.range(START, FINISH).forEach(e -> map2.put(e, "one" + e));
    }

}