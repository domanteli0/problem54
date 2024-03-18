package com.github.domanteli0;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.github.domanteli0.Card.Rank.*;
import static com.github.domanteli0.Card.Suit.*;
import static org.junit.jupiter.api.Assertions.*;

class CardParseTest {

    static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of("5H", new Card(_5, Heart)),
            Arguments.of("QH", new Card(Queen, Heart)),
            Arguments.of("TD", new Card(_10, Diamond)),
            Arguments.of("AC", new Card(Ace, Club))
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void itParses(String input, Card expected_card) throws IllegalArgumentException {
        assertEquals(expected_card, Card.parse(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1J", "6T", "1T"})
    void errorOnInvalidData(String input) {
        assertThrows(IllegalArgumentException.class, () -> Card.parse(input));
    }

}