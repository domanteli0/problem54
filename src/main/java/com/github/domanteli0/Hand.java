package com.github.domanteli0;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;

import static com.github.domanteli0.Card.Rank.*;
import static java.util.stream.Collectors.groupingBy;

public record Hand(List<Card> cards) {
    public Stream<Card.Rank> toRanks() {
        return this.cards().stream().map(Card::rank);
    }

    // TODO: validate `input` length
    // TODO: custom exception type
    public static Hand parse(String input) throws IllegalArgumentException {
        List<Card> cards = Stream
            .of(input.split("\\ "))
            .map(Card::parse)
            .toList();

        return new Hand(cards);
    }

    public static Predicate<Hand> isNPair(int n) {
        return (self) -> self.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2)
            .count() == n;
    }

    public boolean isFlush() {
        return cards.stream()
            .collect(groupingBy((Card::suit)))
            .values().stream()
            .anyMatch((list) -> (long) list.size() == 5);
    }

    public boolean isFullHouse() {
        return isNOfAKind(3).test(this) && isNOfAKind(2).test(this);
    }

    public static Predicate<Hand> isNOfAKind(int n) {
        return (self) -> self.cards.stream()
            .collect(groupingBy((Card::rank)))
            .values().stream()
            .anyMatch((list) -> (long) list.size() == n);
    }

    public boolean isStraight() {
        var thisStream = cards.stream().map(Card::rank).sorted().toList();

        var royalFlushStream = Arrays.stream(values())
            .dropWhile(rank -> !rank.equals(thisStream.get(0)))
            .limit(5);

        return Iterators.elementsEqual(
            thisStream.iterator(),
            royalFlushStream.iterator()
        );
    }

    public boolean isStraightFlush() {
        return isFlush() && isStraight();
    }
}
