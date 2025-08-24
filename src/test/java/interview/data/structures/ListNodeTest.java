package interview.data.structures;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class ListNodeTest {

    public static Stream<Arguments> provideArgumentsForListNodeOfMethodTest() {
        return Stream.of(
                Arguments.of(null, new ListNode(null, null), "Should create node with no value and next"),
                Arguments.of(emptyList(), new ListNode(null, null), "Should create node with no value and next"),
                Arguments.of(List.of(1), new ListNode(1, null), "Should create node with value and with no next"),
                Arguments.of(List.of(1, 2), new ListNode(1, new ListNode(2, null)), "Should create node with value and with next node")
        );
    }

    @ParameterizedTest(name = "test of({0}) = {1}")
    @MethodSource("provideArgumentsForListNodeOfMethodTest")
    public void listNodeOfMethodTest(final Iterable<Integer> listToCheck, final ListNode<Integer> expectedValue, final String errorMessage) {
        //when
        final var actual = ListNode.of(listToCheck);
        //then
        Assertions.assertThat(actual).as(errorMessage).isEqualTo(expectedValue);
    }

    @Test
    public void testCircularBreakerFor_toString_and_equalsTest() {
        //given
        var node1 = ListNode.of(1);
        var node2 = ListNode.of(2);
        var node3 = ListNode.of(3);
        node1.nextNode = node2;
        node2.nextNode = node3;
        node3.nextNode = node2;
        //when
        String string = node1.toString();
        //then
        Assertions.assertThat(string).as("Should break circular node").isEqualTo("[1->2->3]");
    }

    @Test
    public void testCircularBreakerFor_equals_left() {
        //given
        var additional = ListNode.of(10);

        var left1 = ListNode.of(1);
        var left2 = ListNode.of(2);
        var left3 = ListNode.of(3);
        left1.nextNode = left2;
        left2.nextNode = left3;
        left3.nextNode = left2;

        var right1 = ListNode.of(1);
        var right2 = ListNode.of(2);
        var right3 = ListNode.of(3);
        right1.nextNode = right2;
        right2.nextNode = right3;
        right3.nextNode = additional;
        //then
        Assertions.assertThat(left1).as("Should break circular nodes left").isNotEqualTo(right1);
    }


    @Test
    public void testCircularBreakerFor_equals_right() {
        //given
        var additional = ListNode.of(10);

        var left1 = ListNode.of(1);
        var left2 = ListNode.of(2);
        var left3 = ListNode.of(3);
        left1.nextNode = left2;
        left2.nextNode = left3;
        left3.nextNode = additional;

        var right1 = ListNode.of(1);
        var right2 = ListNode.of(2);
        var right3 = ListNode.of(3);
        right1.nextNode = right2;
        right2.nextNode = right3;
        right3.nextNode = right2;
        //then
        Assertions.assertThat(left1).as("Should break circular nodes left").isNotEqualTo(right1);
    }

    @Test
    public void testNotEqualNodes() {
        //given
        var node1 = ListNode.of(1);
        var node2 = ListNode.of(2);
        var node3 = ListNode.of(3);
        node1.nextNode = node2;
        node2.nextNode = node3;
        node3.nextNode = node2;
        //then
        Assertions.assertThat(node1).as("List Nodes with different values should not be equal").isNotEqualTo(node2);
    }

    @Test
    public void testNotEqualNodes_whenSameValue_DifferentChild() {
        //given
        var node1 = ListNode.of(1);
        var node2 = ListNode.of(1);
        var node3 = ListNode.of(3);
        var node4 = ListNode.of(4);
        node1.nextNode = node3;
        node2.nextNode = node4;
        //then
        Assertions.assertThat(node1).as("List Nodes with different child should be not equal").isNotEqualTo(node2);
    }

    @Test
    public void testNotEqualNodes_whenSameValue_DifferentChildrenLength() {
        //given
        var node1 = ListNode.of(1);
        var node2 = ListNode.of(1);
        var node3 = ListNode.of(3);
        var node4 = ListNode.of(3);
        var node5 = ListNode.of(5);
        node1.nextNode = node3;
        node2.nextNode = node4;
        node4.nextNode = node5;
        //then
        Assertions.assertThat(node1).as("List Nodes with different children length are not equal").isNotEqualTo(node2);
    }

    @Test
    public void testEqualNodes_whenSameValue_sameChild() {
        //given
        var node1 = ListNode.of(1);
        var node2 = ListNode.of(1);
        var node3 = ListNode.of(3);
        var node4 = ListNode.of(3);
        node1.nextNode = node3;
        node2.nextNode = node4;
        //then
        Assertions.assertThat(node1).as("List nodes with same child should be equal").isEqualTo(node2);
    }
}