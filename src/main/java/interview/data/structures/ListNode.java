package interview.data.structures;

import java.util.Arrays;
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

    public static <T> ListNode<T> of(T value) {
        if (value != null && value.getClass().isArray()) {
            if (!Number.class.isAssignableFrom(value.getClass().getComponentType()) &&
                    !String.class.isAssignableFrom(value.getClass().getComponentType()) &&
                        !Character.class.isAssignableFrom(value.getClass().getComponentType())) {
                throw new IllegalArgumentException("Object is not an array of the specified type.");
            }
            T[] values = (T[]) value;
            return ListNode.of(Arrays.asList(values));
        }
        return new ListNode(value, null);
    }

    public static <T> ListNode<T> of(T value, ListNode<T> nextNode) {
        return new ListNode(value, nextNode);
    }

    public static <T> ListNode<T> of(Iterable<T> values) {
        if (values == null || !values.iterator().hasNext()) {
            return of((T) null);
        }
        var head = new ListNode<T>();
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
        var nextLeft = leftHead;
        var fastLeft = leftHead;
        var nextRight = rightHead;
        var fastRight = rightHead;
        while (nextLeft != null  && nextRight != null) {
            if (!function.apply(nextLeft, nextRight)) {
                return false;
            }
            nextLeft = nextLeft.nextNode;
            if (fastLeft != null && fastLeft.nextNode != null) {
                fastLeft = fastLeft.nextNode.nextNode;
            } else {
                fastLeft = null;
            }
            nextRight = nextRight.nextNode;
            if (fastRight != null && fastRight.nextNode != null) {
                fastRight = fastRight.nextNode.nextNode;
            } else {
                fastRight = null;
            }
            if ((nextLeft != null && nextLeft == fastLeft) || (nextRight != null && nextRight == fastRight)) {
                return false;
            }
        }
        if (nextLeft == null && nextRight == null) {
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
        var next = listNode.nextNode;
        var fast = listNode.nextNode;
        while (next != null) {
            function.accept(next);
            next = next.nextNode;
            if (fast != null && fast.nextNode != null) {
                fast = fast.nextNode.nextNode;
            } else {
                fast = null;
            }
            if (next != null && fast == next) {
                return;
            }
        }

    }
}
