package com.github.domanteli0;

import java.util.*;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.NonNull;

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
        } else if (compareFullHouse(other) != 0) {
            return compareFullHouse(other);
        } else {
            throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
        }
    }

    private int compareFullHouse(Hand other) {
        var comp11 = this.threeOfAKindRank();
        var comp12 = other.threeOfAKindRank();

        if (Integer.compare(comp11, comp12) == 0 && this.isFullHouse() && other.isFullHouse()) {
            return Integer.compare(this.pairRank(), other.pairRank());
        } else {
            return Integer.compare(comp11, comp12);
        }
    }

    private int pairRank() {
        return cards.stream()
            .collect(groupingBy((Card::rank)))
            .values().stream()
            .filter(list -> (long) list.size() == 2)
            .map(hand -> hand.stream().map(Card::rank))
            .findFirst().orElse(Stream.empty())
            .findFirst().map(r -> r.ordinal() + 1).orElse(0);
    }

    private int threeOfAKindRank() {
        return cards.stream()
            .collect(groupingBy((Card::rank)))
            .values().stream()
            .filter(list -> (long) list.size() == 3)
            .map(hand -> hand.stream().map(Card::rank))
            .findFirst().orElse(Stream.empty())
            .findFirst().map(r -> r.ordinal() + 1).orElse(0);
    }

    private boolean isFullHouse() {
        var it = cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream().toList();

        var i1 = it.get(0);
        var i2 = it.get(1);

        if (i1.size() == 2 && i2.size() == 3) {
            return true;
        } else if (i1.size() == 3 && i2.size() == 2) {
            return true;
        } else {
            return false;
        }
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
