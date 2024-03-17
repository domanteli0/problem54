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
        if (this.isRoyalFlush() && other.isRoyalFlush()) {
            return 0;
        } else if (this.isRoyalFlush() && !other.isRoyalFlush()) {
            return 1;
        } else if (!this.isRoyalFlush() && other.isRoyalFlush()) {
            return -1;
        } // by this point both hands are ot royal flushes

        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }

    private boolean isRoyalFlush() {
        var thisStream = cards.stream()
            .collect(groupingBy((card -> card.suit())))
            .values().stream()
            .filter((list) -> list.stream().count() == 5)
            .map((hand) -> hand.stream().map(card -> card.rank()))
            .findFirst().orElse(Stream.empty())
            .iterator();

        var royalFlushStream = Stream.of(
                _10,
                Jack,
                Queen,
                King,
                Ace
            )
            .iterator();

        return Iterators.elementsEqual(
            thisStream,
            royalFlushStream
        );
    }
}
