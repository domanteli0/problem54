package com.github.domanteli0;

import com.github.domanteli0.Card.Rank;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.github.domanteli0.Card.Rank.*;
import static com.github.domanteli0.Card.Suit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandParseTest {
    static Stream<Arguments> testData() {
        Arguments case1 = Arguments.of(
            "5H 5C 6S 7S KD",
            new Hand(List.of(
                new Card(_5, Heart),
                new Card(_5, Club),
                new Card(_6, Spade),
                new Card(_7, Spade),
                new Card(King, Diamond)
            ))
        );

        Arguments case2 = Arguments.of(
            "3C 3D 3S 9S 9D",
            new Hand(List.of(
                new Card(_3, Club),
                new Card(_3, Diamond),
                new Card(_3, Spade),
                new Card(_9, Spade),
                new Card(_9, Diamond)
            ))
        );

        return Stream.of(case1, case2);
    }

    @ParameterizedTest
    @MethodSource("testData")
    void itParses(String input, Hand expected_card) throws IllegalArgumentException {
        assertEquals(expected_card, Hand.parse(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2C 3S 8S 8D TI"})
    void errorOnInvalidData(String input) {
        assertThrows(IllegalArgumentException.class, () -> Hand.parse(input));
    }

}