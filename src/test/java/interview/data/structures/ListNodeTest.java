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

    public static Stream<Arguments> provideArgumentsForIterableSupport() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3), new ListNode(1, new ListNode(2, new ListNode<>(3, null))), "Should create node with value and 2 next nodes"),
                Arguments.of(null, new ListNode(null, null), "Should create node with no value and next"),
                Arguments.of(emptyList(), new ListNode(null, null), "Should create node with no value and next"),
                Arguments.of(List.of(1), new ListNode(1, null), "Should create node with value and with no next"),
                Arguments.of(List.of(1, 2), new ListNode(1, new ListNode(2, null)), "Should create node with value and with next node")
        );
    }

    @ParameterizedTest(name = "test of({0}) = {1}")
    @MethodSource("provideArgumentsForIterableSupport")
    public void canInitliseListWithIterablesTest(final Iterable<Integer> listToCheck, final ListNode<Integer> expectedValue, final String errorMessage) {
        //when
        final var actual = ListNode.of(listToCheck);
        //then
        Assertions.assertThat(actual).as(errorMessage).isEqualTo(expectedValue);
    }

    public static Stream<Arguments> provideArgumentsForTableInitliseTest() {
        return Stream.of(
                Arguments.of(null, new ListNode(null, null), "Should create node with no value and next"),
                Arguments.of(new Integer[]{}, new ListNode(null, null), "[Integer]Should create node with no value and next"),
                Arguments.of(new Integer[]{1}, new ListNode(1, null), "[Integer]Should create node with value and with no next"),
                Arguments.of(new Integer[]{1, 2}, new ListNode(1, new ListNode(2, null)), "[Integer]Should create node with value and with next node"),
                Arguments.of(new Integer[]{1, 2, 3}, new ListNode(1, new ListNode(2, new ListNode<>(3, null))), "[Integer]Should create node with value and 2 next nodes"),
                Arguments.of(new String[]{"1"}, new ListNode("1", null), "[String]Should create node with value and with no next"),
                Arguments.of(new String[]{"1", "2"}, new ListNode("1", new ListNode("2", null)), "[String]Should create node with value and with next node"),
                Arguments.of(new String[]{"1", "2", "3"}, new ListNode("1", new ListNode("2", new ListNode<>("3", null))), "[String]Should create node with value and 2 next nodes"),
                Arguments.of(new Double[]{1.0}, new ListNode(1.0, null), "[Double]Should create node with value and with no next"),
                Arguments.of(new Double[]{1.0, 2.0}, new ListNode(1.0, new ListNode(2.0, null)), "[Double]Should create node with value and with next node"),
                Arguments.of(new Double[]{1.0, 2.0, 3.0}, new ListNode(1.0, new ListNode(2.0, new ListNode<>(3.0, null))), "[Double]Should create node with value and 2 next nodes"),
                Arguments.of(new Character[]{'1'}, new ListNode('1', null), "[Character]Should create node with value and with no next"),
                Arguments.of(new Character[]{'1', '2'}, new ListNode('1', new ListNode('2', null)), "[Character]Should create node with value and with next node"),
                Arguments.of(new Character[]{'1', '2', '3'}, new ListNode('1', new ListNode('2', new ListNode<>('3', null))), "[Character]Should create node with value and 2 next nodes")
                );
    }

    @ParameterizedTest(name = "test of({0}) = {1}")
    @MethodSource("provideArgumentsForTableInitliseTest")
    public void canInitliseListWithTablesOfStringAndBoxedPrimitives(final Object[] listToCheck, final ListNode expectedValue, final String errorMessage) {
        //when
        final var actual = ListNode.of(listToCheck);
        //then
        Assertions.assertThat(actual).as(errorMessage).isEqualTo(expectedValue);
    }

    @Test
    public void primitiveTypesArraysNotSupported() {
        //then
        Assertions.assertThatThrownBy(() -> ListNode.of(new int[]{1})).isInstanceOf(IllegalArgumentException.class).as("should not support primitive tables");
        Assertions.assertThatThrownBy(() -> ListNode.of(new char[]{'1'})).isInstanceOf(IllegalArgumentException.class).as("should not support primitive tables");
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

    @Test
    public void testToStringAndEquals_CornerCasesFound() {
        //given
        var string = ListNode.of(List.of(1, 1, 2, 2, 3, 4)).toString();
        //then
        Assertions.assertThat(string).as("List nodes with same child should be equal").isEqualTo("[1->1->2->2->3->4]");
    }
}