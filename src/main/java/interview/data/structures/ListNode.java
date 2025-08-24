package interview.data.structures;

import java.util.Objects;

public final class ListNode<T> {
    public T value = null;
    public ListNode<T> nextNode = null;

    private ListNode() {}

    public ListNode(final T value, final ListNode<T> nextNode) {
        this.value = value;
        this.nextNode = nextNode;
    }

    public static <T> ListNode of(T value){
        return new ListNode(value, null);
    }

    public static <T> ListNode of(T value, ListNode<T> nextNode){
        return new ListNode(value, nextNode);
    }

    public static <T> ListNode of(Iterable<T> value){
        if(value == null || !value.iterator().hasNext()){
            of((T)null);
        }
        return new ListNode(value, null);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final ListNode<?> listNode = (ListNode<?>) o;
        return Objects.equals(value, listNode.value) && Objects.equals(nextNode, listNode.nextNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, nextNode);
    }
}
