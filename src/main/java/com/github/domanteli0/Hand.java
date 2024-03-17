package com.github.domanteli0;

import java.util.*;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.naming.OperationNotSupportedException;

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
    public int compareTo(@NonNull Hand other) {
        if (compareStraightFlushes(other) != 0) {
            return compareStraightFlushes(other);
        } else if (compareFourOfAKind(other) != 0) {
            return compareFourOfAKind(other);
        } else {
            throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
        }
    }

    private int compareFullHouse(Hand other) {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }

    private int fullHouseRank() {
        throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }

    private int compareFourOfAKind(Hand other) {
        var comp1 = fourOfAKindRank();
        var comp2 = other.fourOfAKindRank();

        if (Integer.compare(comp1, comp2) == 0 && isFourOfAKind() && other.isFourOfAKind()) {
            return Integer.compare(
                cards.stream()
                    .collect(groupingBy((card -> card.rank())))
                    .values().stream()
                    .filter((list) -> list.stream().count() != 4)
                    .map((hand) -> hand.stream().map(card -> card.rank()))
                    .findFirst().orElse(Stream.empty())
                    .findFirst().map(r -> r.ordinal() + 1).orElse(0),
                other.cards.stream()
                    .collect(groupingBy((card -> card.rank())))
                    .values().stream()
                    .filter((list) -> list.stream().count() != 4)
                    .map((hand) -> hand.stream().map(card -> card.rank()))
                    .findFirst().orElse(Stream.empty())
                    .findFirst().map(r -> r.ordinal() + 1).orElse(0)
            );
        }

        return Integer.compare(comp1, comp2);
    }

    private boolean isFourOfAKind() {
        return cards.stream()
            .collect(groupingBy((Card::rank)))
            .values().stream()
            .anyMatch((list) -> list.stream().count() == 4);
    }

    private int fourOfAKindRank() {
        return cards.stream()
            .collect(groupingBy((card -> card.rank())))
            .values().stream()
            .filter((list) -> list.stream().count() == 4)
            .map((hand) -> hand.stream().map(card -> card.rank()))
            .findFirst().orElse(Stream.empty())
            .findFirst().map(r -> r.ordinal() + 5).orElse(0);
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
