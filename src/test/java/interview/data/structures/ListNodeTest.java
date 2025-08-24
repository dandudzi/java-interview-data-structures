package interview.data.structures;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ListNodeTest {

    public static Stream<Arguments> provideArgumentsForListNodeOfMethodTest() {
        return Stream.of(
                Arguments.of(null, new ListNode(null, null), "Should create node with no value and next")
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
}