import java.util.concurrent.locks.ReentrantReadWriteLock;

class ConcurrentTreeMap<K extends Comparable<K>, V> {
    private Node root;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private class Node {
        K key;
        V value;
        Node left, right;
        final ReentrantReadWriteLock nodeLock = new ReentrantReadWriteLock();

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            root = put(root, key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
    private Node put(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value);
        }

        node.nodeLock.writeLock().lock();
        try {
            if (key.compareTo(node.key) < 0) {
                node.left = put(node.left, key, value);
            } else if (key.compareTo(node.key) > 0) {
                node.right = put(node.right, key, value);
            } else {
                // Key already exists, update the value
                node.value = value;
            }
            return node;
        } finally {
            node.nodeLock.writeLock().unlock();
        }
    }
    public V get(K key) {
        lock.readLock().lock();
        try {
            return get(root, key);
        } finally {
            lock.readLock().unlock();
        }
    }

    private V get(Node node, K key) {
        if (node == null) {
            return null;
        }

        node.nodeLock.readLock().lock();
        try {
            if (key.compareTo(node.key) < 0) {
                return get(node.left, key);
            } else if (key.compareTo(node.key) > 0) {
                return get(node.right, key);
            } else {
                return node.value;
            }
        } finally {
            node.nodeLock.readLock().unlock();
        }
    }
}
