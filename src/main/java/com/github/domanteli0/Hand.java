package com.github.domanteli0;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public record Hand(List<Card> cards) {
    public static final int HAND_SIZE = 5;

    // TODO: validate `input` length
    // TODO: custom exception type
    public static Hand parse(String input) throws IllegalArgumentException {
        List<Card> cards = Stream
            .of(input.split("\\ "))
            .map(Card::parse)
            .toList();

        return new Hand(cards);
    }
}
