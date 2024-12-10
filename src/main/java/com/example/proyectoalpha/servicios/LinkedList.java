package com.example.proyectoalpha.servicios;

public class LinkedList<T> {
    private class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public Node() {
            this.data = null;
            this.next = null;
        }
    }

    private Node<T> primero;
    private Node<T> ultimo;
    private int size;

    public LinkedList() {
        primero = null;
        ultimo = null;
        size = 0;
    }

    public boolean is_empty() {
        return size == 0;
    }

    public void insertarCabeza(T dato) {
        Node<T> neo = new Node<T>(dato);
        if (is_empty()) {
            this.ultimo = neo;
        }
        neo.next = this.primero;
        this.primero = neo;
        size++;
    }

    public T eliminarCabeza() {
        if (is_empty()) {
            return null;
        }
        Node<T> temp = primero;
        primero = primero.next;
        size--;
        return temp.data;
    }

    public T obtener(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Node<T> current = primero;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public void eliminarPorValor(T valor) {
        if (is_empty()) {
            return;
        }

        if (primero.data.equals(valor)) {
            eliminarCabeza();
            return;
        }

        Node<T> actual = primero;
        while (actual.next != null) {
            if (actual.next.data.equals(valor)) {
                actual.next = actual.next.next;
                size--;
                if (actual.next == null) {
                    ultimo = actual;
                }
                return;
            }
            actual = actual.next;
        }
    }
}
