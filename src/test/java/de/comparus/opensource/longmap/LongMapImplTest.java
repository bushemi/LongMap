package de.comparus.opensource.longmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

import static java.util.Objects.isNull;

@RunWith(JUnit4.class)
public class LongMapImplTest {
    private static final int START = 0;
    private static final int FINISH = 50000;
    Map<Long, String> map = new HashMap<>();
    LongMap<String> map2 = new LongMapImpl<>();

    @Test
    public void putHashMap() {
        System.out.println("start");
        long start = System.currentTimeMillis();
        fillTheHMap();
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
        fillTheHMap();
        System.out.println("start");
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> {
            String obj = map.get(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from getFromMyMap");
        });
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void getFromMyMap() {
        fillMyMap();
        System.out.println("start");
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> {
            String obj = map2.get(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from getFromMyMap");
        });
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void remove() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void containsKey() {
    }

    @Test
    public void containsValue() {
    }

    @Test
    public void keys() {
    }

    @Test
    public void values() {
    }

    @Test
    public void size() {
    }

    @Test
    public void clear() {
    }

    private void fillTheHMap() {
        LongStream.range(START, FINISH).forEach(e -> map.put(e, "one" + e));
    }

    private void fillMyMap() {
        LongStream.range(START, FINISH).forEach(e -> map2.put(e, "one" + e));
    }

}