package com.github.domanteli0;

import com.github.domanteli0.Card.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ArgumentUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CardParseTest {

    static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of("5H", new Card(Rank._5, Suit.Heart)),
            Arguments.of("QH", new Card(Rank.Queen, Suit.Heart)),
            Arguments.of("TD", new Card(Rank._10, Suit.Diamond)),
            Arguments.of("AC", new Card(Rank.Ace, Suit.Club))
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