package com.github.domanteli0;

import com.google.common.collect.Streams;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class HandComparator implements Comparator<Hand> {
    @Override
    public int compare(Hand left, Hand right) {
        return new RuleSet(
            Rule.define(
                "Royal/Straight Flush",
                Hand::isStraightFlush,
                HandComparator::compareStraightFlushes
            ),
            Rule.define(
                "Four-of-a-Kind",
                Hand.isNOfAKind(4),
                HandComparator.compareNOfAKind(4)
            ),
            Rule.define(
                "Full House",
                Hand::isFullHouse,
                HandComparator.compareNOfAKind(3)
            ),
            Rule.define(
                "Flush",
                Hand::isFlush,
                HandComparator::compareFlush
            ),
            Rule.define(
                "Straight",
                Hand::isStraight,
                HandComparator::compareStraightFlushes
            ),
            Rule.define(
                "Three-of-a-kind",
                Hand.isNOfAKind(3),
                HandComparator.compareNOfAKind(3)
            ),
            Rule.define(
                "Two Pair",
                Hand.isNPair(2),
                HandComparator.compareNOfAKind(2)
            ),
            Rule.define(
                "One Pair",
                Hand.isNOfAKind(2),
                HandComparator.compareNOfAKind(2)
            ),
            Rule.define(
                "High Card",
                HandComparator::always,
                HandComparator::compareHighRanks
            )
        ).compare(left, right);
    }

    private static int compareStraightFlushes(Hand left, Hand right) {
        return Integer.compare(straightRank(left), straightRank(right));
    }

    private static int straightRank(Hand hand) {
        return hand.cards().stream()
            .map(Card::rank)
            .sorted(Comparator.reverseOrder())
            .findFirst().get().ordinal();
    }

    private static int compareFlush(Hand left, Hand right) {
        return compareHighRanks(
            left.cards().stream().map(Card::rank).sorted(Comparator.reverseOrder()),
            right.cards().stream().map(Card::rank).sorted(Comparator.reverseOrder())
        );
    }

    private static BiFunction<Hand, Hand, Integer> compareNOfAKind(int n) {
        return (left, right) -> compareHighRanks(
            getPairsAndLeftoversInOrder(left, n),
            getPairsAndLeftoversInOrder(right, n)
        );
    }

    private static int compareHighRanks(Hand left, Hand right) {
        return compareHighRanks(
            left.toRanks().sorted(Comparator.reverseOrder()),
            right.toRanks().sorted(Comparator.reverseOrder())
        );
    }

    private static int compareHighRanks(Stream<Card.Rank> leftRanks, Stream<Card.Rank> rightRanks) {
        var left = leftRanks.toList();
        var right = rightRanks.toList();

        if (left.size() != right.size())
            throw new IllegalArgumentException("PROVIDED HANDS DO NOT HAVE EQUAL AMOUNT OF CARDS");

        var comparisons = Streams.zip(
            left.stream(),
            right.stream(),
            (l, r) -> l.compareTo(r)
        );

        return comparisons.filter(c -> c != 0).findFirst().orElse(0);
    }

    private static Stream<Card.Rank> getPairsAndLeftoversInOrder(Hand hand, int pairSize) {
        var iter = hand.cards().stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .collect(Collectors.partitioningBy(l -> l.size() == pairSize));

        var pairs = iter.get(true).stream()
            .map(List::getFirst)
            .map(Card::rank)
            .sorted(Comparator.reverseOrder());

        var leftovers = iter.get(false).stream()
            .flatMap(l -> l.stream().map(Card::rank))
            .sorted(Comparator.reverseOrder());

        return Streams.concat(pairs, leftovers);
    }

    private static boolean always(Hand hand) {
        return true;
    }
}
