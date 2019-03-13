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
    private static final int RESULT_QUANTITY = 20;

    @Test
    public void putHashMap() {
        Map<Long, String> map = new HashMap<>();
        System.out.println("start putHashMap");
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            map = new HashMap<>();
            long start = System.currentTimeMillis();
            fillTheHashMap(map);
            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));
        System.out.println("map.size() = " + map.size());
    }

    @Test
    public void putMyMap() {
        LongMap<String> map2 = new LongMapImpl<>();
        System.out.println("start putMyMap");
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            map2 = new LongMapImpl<>();
            long start = System.currentTimeMillis();
            fillMyMap(map2);
            long finish = System.currentTimeMillis();
            results += (finish - start);
        }

        System.out.println("averageTime = " + (results / RESULT_QUANTITY));
        System.out.println("map2.size() = " + map2.size());
    }

    @Test
    public void getFromHashMap() {
        Map<Long, String> map = new HashMap<>();
        fillTheHashMap(map);
        System.out.println("start getFromHashMap");
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long start = System.currentTimeMillis();
            LongStream.range(START, FINISH).forEach(e -> {
                String obj = map.get(e);
                if (isNull(obj))
                    throw new RuntimeException("Fail from getFromHashMap");
            });
            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));
    }

    @Test
    public void getFromMyMap() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);
        System.out.println("start getFromMyMap");
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long start = System.currentTimeMillis();
            LongStream.range(START, FINISH).forEach(e -> {
                String obj = map2.get(e);
                if (isNull(obj))
                    throw new RuntimeException("Fail from getFromMyMap");
            });
            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));
    }

    @Test
    public void removeFromHashMap() {
        Map<Long, String> map;
        System.out.println("start removeFromHashMap");
        long results = 0;
        List<Long> range = LongStream.range(START, FINISH).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            map = new HashMap<>();
            fillTheHashMap(map);
            int sizeBefore = map.size();
            assertEquals(FINISH, sizeBefore);
            long start = System.currentTimeMillis();
            Map<Long, String> finalMap = map;
            range.forEach(e -> {
                String obj = finalMap.remove(e);
                if (isNull(obj))
                    throw new RuntimeException("Fail from removeFromHashMap");
            });
            long finish = System.currentTimeMillis();
            results += (finish - start);

            int sizeAfter = map.size();
            assertEquals(String.format("failed removeFromHashMap on the %d iteration ", i), 0, sizeAfter);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));
    }


    @Test
    public void removeFromMyMap() {
        LongMap<String> map2;
        System.out.println("start removeFromMyMap");
        long results = 0;
        List<Long> range = LongStream.range(START, FINISH).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            map2 = new LongMapImpl<>();
            fillMyMap(map2);

            long sizeBefore = map2.size();
            assertEquals(FINISH, sizeBefore);

            long start = System.currentTimeMillis();
            LongMap<String> finalMap = map2;
            range.forEach(e -> {
                String obj = finalMap.remove(e);
                if (isNull(obj))
                    throw new RuntimeException("Fail from removeFromMyMap");
            });
            long finish = System.currentTimeMillis();
            results += (finish - start);

            long sizeAfter = map2.size();
            assertEquals(String.format("failed removeFromMyMap on the %d iteration ", i), 0, sizeAfter);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));
    }

    @Test
    public void isEmpty() {
        LongMap<String> map2 = new LongMapImpl<>();
        assertTrue(map2.isEmpty());
        map2.put(1L, "test");
        assertFalse(map2.isEmpty());
        map2.remove(1L);
        assertTrue(map2.isEmpty());
    }

    @Test
    public void containsKeyHashMap() {
        Map<Long, String> map = new HashMap<>();
        fillTheHashMap(map);
        System.out.println("start containsKeyHashMap");
        Random random = new Random();
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long key = (long) random.nextInt(FINISH);
            long start = System.currentTimeMillis();
            boolean containsKey = map.containsKey(key);

            assertTrue("failed containsKeyHashMap", containsKey);
            containsKey = map.containsKey((long) FINISH + 1);
            assertFalse(containsKey);

            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void containsKeyMyMap() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);
        System.out.println("start containsKeyMyMap");
        Random random = new Random();

        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long key = (long) random.nextInt(FINISH);
            long start = System.currentTimeMillis();
            boolean containsKey = map2.containsKey(key);

            assertTrue("failed containsKeyMyMap", containsKey);
            containsKey = map2.containsKey((long) FINISH + 1);
            assertFalse(containsKey);

            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void containsValueHashMap() {
        Map<Long, String> map = new HashMap<>();
        fillTheHashMap(map);
        System.out.println("start containsValueHashMap");
        Random random = new Random();
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long key = (long) random.nextInt(FINISH);
            long start = System.currentTimeMillis();
            boolean containsValue = map.containsValue("one" + key);

            assertTrue("fail from containsValueHashMap", containsValue);
            containsValue = map.containsValue("one" + FINISH);
            assertFalse(containsValue);

            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void containsValueMyMap() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);
        System.out.println("start containsValueMyMap");
        Random random = new Random();

        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long key = (long) random.nextInt(FINISH);
            long start = System.currentTimeMillis();
            boolean containsValue = map2.containsValue("one" + key);

            assertTrue("fail from containsValueMyMap", containsValue);
            containsValue = map2.containsValue("one" + FINISH);
            assertFalse(containsValue);

            long finish = System.currentTimeMillis();
            results += (finish - start);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void keysHashMap() {
        Map<Long, String> map = new HashMap<>();
        fillTheHashMap(map);

        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long start = System.currentTimeMillis();
            Set<Long> keys = map.keySet();

            long finish = System.currentTimeMillis();
            results += (finish - start);
            assertEquals(FINISH, keys.size());
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void keysMyMap() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);
        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long start = System.currentTimeMillis();
            long[] keys = map2.keys();

            long finish = System.currentTimeMillis();
            results += (finish - start);
            assertEquals(FINISH, keys.length);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void valuesHashMap() {
        Map<Long, String> map = new HashMap<>();
        fillTheHashMap(map);

        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long start = System.currentTimeMillis();
            Collection<String> values = map.values();

            long finish = System.currentTimeMillis();
            results += (finish - start);
            assertEquals(FINISH, values.size());
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void valuesMyMap() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);

        long results = 0;
        for (int i = 0; i < RESULT_QUANTITY; i++) {
            long start = System.currentTimeMillis();
            String[] values = map2.values();

            long finish = System.currentTimeMillis();
            results += (finish - start);
            assertEquals(FINISH, values.length);
        }
        System.out.println("averageTime = " + (results / RESULT_QUANTITY));

    }

    @Test
    public void size() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);
        System.out.println("start size");
        long start = System.currentTimeMillis();

        long size = map2.size();
        assertEquals(FINISH, size);

        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void clear() {
        LongMap<String> map2 = new LongMapImpl<>();
        fillMyMap(map2);
        System.out.println("start clear");
        long start = System.currentTimeMillis();

        map2.clear();

        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    private void fillTheHashMap(Map<Long, String> map) {
        LongStream.range(START, FINISH).forEach(e -> map.put(e, "one" + e));
    }

    private void fillMyMap(LongMap<String> map) {
        LongStream.range(START, FINISH).forEach(e -> map.put(e, "one" + e));
    }

}