package de.comparus.opensource.longmap;

import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public LongMapImpl() {
        this.table = new Node[DEFAULT_CAPACITY];
        this.size = 0;
    }

    private static class Node<V> {
        long key;
        V value;
        Node<V> next;

        Node(long key, V value, Node<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public V put(long key, V value) {
        if (size >= table.length * DEFAULT_LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Node<V> current = table[index];

        while (current != null) {
            if (current.key == key) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }

        table[index] = new Node<>(key, value, table[index]);
        size++;
        return null;
    }

    @Override
    public V get(long key) {
        int index = getIndex(key);
        Node<V> current = table[index];

        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    @Override
    public V remove(long key) {
        int index = getIndex(key);
        Node<V> current = table[index];
        Node<V> previous = null;

        while (current != null) {
            if (current.key == key) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(long key) {
        int index = getIndex(key);
        Node<V> current = table[index];

        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<V> entry : table) {
            Node<V> current = entry;
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public long[] keys() {
        long[] keys = new long[size];
        int index = 0;

        for (Node<V> entry : table) {
            Node<V> current = entry;
            while (current != null) {
                keys[index++] = current.key;
                current = current.next;
            }
        }

        return keys;
    }

    @Override
    public V[] values() {
        @SuppressWarnings("unchecked")
        V[] values = Arrays.copyOf((V[]) new Object[size], size);
        int index = 0;

        for (Node<V> entry : table) {
            Node<V> current = entry;
            while (current != null) {
                values[index++] = current.value;
                current = current.next;
            }
        }

        return values;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    private int getIndex(long key) {
        return (int) (key % table.length);
    }

    private void resize() {
        int newCapacity = table.length * 2;

        @SuppressWarnings("unchecked")
        Node<V>[] newTable = new Node[newCapacity];

        for (Node<V> entry : table) {
            Node<V> current = entry;
            while (current != null) {
                int newIndex = (int) (current.key % newCapacity);
                Node<V> temp = current.next;
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = temp;
            }
        }

        table = newTable;
    }
}
