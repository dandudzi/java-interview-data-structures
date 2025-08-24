package interview.data.structures;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class ListNode<T> {
    public T value = null;
    public ListNode<T> nextNode = null;

    private ListNode() {
    }

    ListNode(final T value, final ListNode<T> nextNode) {
        this.value = value;
        this.nextNode = nextNode;
    }

    public static <T> ListNode of(T value) {
        return new ListNode(value, null);
    }

    public static <T> ListNode of(T value, ListNode<T> nextNode) {
        return new ListNode(value, nextNode);
    }

    public static <T> ListNode of(Iterable<T> values) {
        if (values == null || !values.iterator().hasNext()) {
            return of((T) null);
        }
        var head = new ListNode<>();
        var next = head;
        var prev = head;
        for (T value : values) {
            next.value = value;
            next.nextNode = new ListNode<>();
            prev = next;
            next = next.nextNode;
        }
        prev.nextNode = null;
        return head;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final ListNode<T> listNode = (ListNode<T>) o;
        return circularBreaker(this, listNode, (left, right) -> Objects.equals(left.value, right.value));
    }

    private static <T> boolean circularBreaker(ListNode<T> leftHead, ListNode<T> rightHead, BiFunction<ListNode<T>, ListNode<T>, Boolean> function) {
        Set<ListNode<T>> visitedLeft = new HashSet<>();
        Set<ListNode<T>> visitedRight = new HashSet<>();
        var left = leftHead;
        var right = rightHead;
        while (left != null && right != null) {
            if (visitedLeft.contains(left) || visitedRight.contains(right)) {
                return false;
            }
            if (!function.apply(left, right)) {
                return false;
            }
            visitedRight.add(right);
            visitedLeft.add(left);
            left = left.nextNode;
            right = right.nextNode;
        }
        if (left == null && right == null) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[" + value);

        circularBreaker(this, (ListNode<T> node) -> stringBuilder.append("->" + node.value));

        stringBuilder.append("]");
        return stringBuilder.toString();
    }


    private static <T> void circularBreaker(ListNode<T> listNode, Consumer<ListNode<T>> function) {
        Set<ListNode<T>> visitedNodes = new HashSet<>();
        var next = listNode.nextNode;
        while (next != null) {
            if (visitedNodes.contains(next)) {
                break;
            }
            function.accept(next);
            visitedNodes.add(next);
            next = next.nextNode;
        }

    }
}
