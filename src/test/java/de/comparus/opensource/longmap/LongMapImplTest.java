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
    public static final int START = 0;
    public static final int FINISH = 50000;
    Map<Long, String> map = new HashMap<>();
    LongMap<String> map2 = new LongMapImpl<>();

    @Test

    public void putHashMap() throws Exception {
//        System.out.println("start");
//        long start = System.nanoTime();
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> map.put(e, "one" + e));
//        long finish = System.nanoTime();
        long finish = System.currentTimeMillis();
//        System.out.println("finish-start = " + (finish - start));
        System.out.println("map.size() = " + map.size());
    }

    @Test
    public void putMyMap() throws Exception {
//        System.out.println("start");
//        long start = System.nanoTime();
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> map2.put(e, "one" + e));
//        long finish = System.nanoTime();
        long finish = System.currentTimeMillis();
//        System.out.println("finish-start = " + (finish - start));
        System.out.println("map2.size() = " + map2.size());
    }

    @Test
    public void getFromHashMap() throws Exception {
        putHashMap();
        System.out.println("start");
//        long start = System.nanoTime();
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> map.get(e));
//        long finish = System.nanoTime();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void getFromMyMap() throws Exception {
        putMyMap();
        System.out.println("start");
//        long start = System.nanoTime();
        long start = System.currentTimeMillis();
        LongStream.range(START, FINISH).forEach(e -> {
            String obj = map2.get(e);
            if (isNull(obj))
                throw new RuntimeException("Fail from getFromMyMap");
        });
//        long finish = System.nanoTime();
        long finish = System.currentTimeMillis();
        System.out.println("finish-start = " + (finish - start));
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void isEmpty() throws Exception {
    }

    @Test
    public void containsKey() throws Exception {
    }

    @Test
    public void containsValue() throws Exception {
    }

    @Test
    public void keys() throws Exception {
    }

    @Test
    public void values() throws Exception {
    }

    @Test
    public void size() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

}