/*Implementación de un binarytree genérico proporcionada por ChatGPT. */

import java.util.*;
public class BinarySearchTree<K extends Comparable<K>, V> {
    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
    }

    private Node root;

    /** Inserta un nuevo nodo en el árbol */
    public void insert(K key, V value) {
        root = insertRec(root, key, value);
    }

    private Node insertRec(Node root, K key, V value) {
        if (root == null) return new Node(key, value);

        if (key.compareTo(root.key) < 0)
            root.left = insertRec(root.left, key, value);
        else if (key.compareTo(root.key) > 0)
            root.right = insertRec(root.right, key, value);
        else
            root.value = value; 

        return root;
    }

    /** Encuentra un nodo en el árbol */
    public V search(K key) {
        return searchRec(root, key);
    }

    private V searchRec(Node root, K key) {
        if (root == null) return null;
        if (key.compareTo(root.key) == 0) return root.value;

        return key.compareTo(root.key) < 0 ? searchRec(root.left, key) : searchRec(root.right, key);
    }

    /** Crea un lista, luego recorre todo el árbol ordenandolo y dejandolo en la lista del inicio. */
    public List<V> inOrderTraversal() {
        List<V> lista = new ArrayList<>();
        inOrderRec(root, lista);
        return lista;
    }

    private void inOrderRec(Node root, List<V> lista) {
        if (root != null) {
            inOrderRec(root.left, lista);
            lista.add(root.value);
            inOrderRec(root.right, lista);
        }
    }
}
