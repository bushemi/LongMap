package de.comparus.opensource.longmap;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LongMapImpl<V> implements LongMap<V> {

    private short mapSize = 10000;

    private Node<V>[] map = new Node[mapSize];
    private long size = 0;

    private Class valuesClass;


    public V put(long key, V value) {
        if (nonNull(value) && isNull(valuesClass)) {
            valuesClass = value.getClass();
        }
        int hash = getHash(key);
        int i = hash % mapSize;
        Node<V> node = map[i];
        if (isNull(node)) {
            setNewNode(key, value, hash, i);
            size++;
        } else {
            if (node.key == key) {
                node.value = value;
            } else {
                Node<V> neededNode = getNeededNode(node, key);
                neededNode.next = new Node<>(value, key, null);
                size++;
            }
        }
        return value;
    }

    private Node<V> getNeededNode(Node<V> node, long key) {
        Node<V> next = node.next;
        if (isNull(next)) {
            return node;
        } else {
            if (next.key == key) {
                return next;
            } else {
                return getNeededNode(next, key);
            }
        }
    }

    private void setNewNode(long key, V value, int hash, int i) {
        Node<V> newNode = new Node<>(value, key, null);
        map[i] = newNode;
    }

    private V findValueByKey(Node<V> node, long key) {
        Node<V> next = node.next;
        if (isNull(next)) {
            return null;
        } else {
            if (next.key == key) {
                return next.value;
            } else {
                return findValueByKey(next, key);
            }
        }
    }

    public V get(long key) {
        int hash = getHash(key);
        int i = hash % mapSize;
        Node<V> node = map[i];
        if (key == node.key) {
            return node.value;
        } else {
            return findValueByKey(node, key);
        }
    }

    public V remove(long key) {
        int hash = getHash(key);
        int i = hash % mapSize;
        Node<V> node = map[i];
        if (isNull(node)) return null;
        if (key == node.key) {
            if (isNull(node.next)) {
                map[i] = null;
                size--;
            } else {
                map[i] = node.next;
                size--;
            }
            return node.value;
        } else {
            size--;
            return removeNodeByKey(node, key);
        }
    }

    private V removeNodeByKey(Node<V> node, long key) {
        Node<V> next = node.next;
        if (next.key == key) {
            return next.value;
        } else {
            return removeNodeByKey(next, key);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        V v = get(key);
        return nonNull(v);
    }

    public boolean containsValue(V value) {
        Optional<V> first = Arrays.stream(map).parallel()
                .filter(Objects::nonNull)
                .map(Node::getLinkedValues)
                .flatMap(Collection::stream).filter(e -> e.equals(value)).findFirst();

        return first.isPresent();
    }

    public long[] keys() {
        return Arrays.stream(map).parallel()
                .filter(Objects::nonNull)
                .map(Node::getLinkedKeys)
                .flatMap(Collection::stream)
                .mapToLong(e -> e)
                .toArray();
    }

    public V[] values() {
        Object[] values = Arrays.stream(map).parallel()
                .filter(Objects::nonNull)
                .map(Node::getLinkedValues)
                .flatMap(Collection::stream)
                .toArray();
        V[] arrayOfValues = (V[]) Array.newInstance(values[0].getClass(), values.length);
        System.arraycopy(values, 0, arrayOfValues, 0, values.length);

        return arrayOfValues;
    }

    public long size() {
        return size;
    }

    public void clear() {
        map = new Node[mapSize];
        size = 0;
    }

    private int getHash(long n) {
        return Long.hashCode(n);
    }

    private class Node<V> {

        private V value;
        private long key;
        private Node<V> next;

        Node(V value, long key, Node<V> next) {
            this.value = value;
            this.key = key;
            this.next = next;
        }

        List<V> getLinkedValues() {
            List<V> list = new ArrayList<>();
            list.add(value);
            if (nonNull(next)) {
                list.addAll(next.getLinkedValues());
            }
            return list;
        }

        List<Long> getLinkedKeys() {
            List<Long> list = new ArrayList<>();
            list.add(key);
            if (nonNull(next)) {
                list.addAll(next.getLinkedKeys());
            }
            return list;
        }

    }
}
