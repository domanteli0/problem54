package com.github.domanteli0;

import java.util.*;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;

import static com.github.domanteli0.Card.Rank.*;
import static java.util.stream.Collectors.groupingBy;

public record Hand(List<Card> cards) implements Comparable<Hand> {
    public static final int HAND_SIZE = 5;

    // TODO: validate `input` length
    // TODO: custom exception type
    public static Hand parse(String input) throws IllegalArgumentException {
        List<Card> cards = Stream
            .of(input.split("\\ "))
            .map(Card::parse)
            // TODO: move sorting to constructor
            .sorted(Comparator.comparing(Card::rank))
            .toList();

        return new Hand(cards);
    }

    @Override
    public int compareTo(Hand other) {
        return compareStraightFlushes(other);
    }

    private int compareStraightFlushes(Hand other) {
        return Integer.compare(straightFlushRank(), other.straightFlushRank());
    }

    // Returns the rank of a straight flush, counting from 6,
    // returns 0, if it is not a straight flush
    private int straightFlushRank() {
        return cards.stream()
            .collect(groupingBy((card -> card.suit())))
            .values().stream()
            .filter((list) -> list.stream().count() == 5)
            .map((hand) -> hand.stream().map(card -> card.rank()))
            .findFirst().orElse(Stream.empty())
            .findFirst().map(rank -> rank.ordinal() + 6).orElse(0);
    }
}
