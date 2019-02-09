package de.comparus.opensource.longmap;

import static java.util.Objects.isNull;

public class LongMapImpl<V> implements LongMap<V> {

    private short mapSize = Short.MAX_VALUE;
    private Node<V>[] map = new Node[mapSize];
    private long size = 0;

    public V put(long key, V value) {
        int hash = getHash(key);
        int i = hash % mapSize;
        Node<V> node = map[i];
        if (isNull(node)) {
            setNewNode(key, value, hash, i);
            size++;
        } else {
            if (node.key == key) {
                node.hash = hash;
                node.value = value;
            } else {
                Node<V> neededNode = getNeededNode(node, key);
                neededNode.next = new Node<>(hash, value, key, null);
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
        Node<V> newNode = new Node<>(hash, value, key, null);
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
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        return false;
    }

    public boolean containsValue(V value) {
        return false;
    }

    public long[] keys() {
        return null;
    }

    public V[] values() {
        return null;
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
        private int hash;
        private V value;
        private long key;
        private Node<V> next;

        Node(int hash, V value, long key, Node<V> next) {
            this.hash = hash;
            this.value = value;
            this.key = key;
            this.next = next;
        }

    }
}
