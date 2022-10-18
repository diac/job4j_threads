package linked;

public class Node<T> {

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    private final Node<T> next;
    private final T value;

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
