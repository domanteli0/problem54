package com.github.domanteli0;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;
import com.google.common.collect.Streams;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.github.domanteli0.Card.Rank.*;
import static java.util.stream.Collectors.groupingBy;

public record Hand(List<Card> cards) implements Comparable<Hand> {
    public Stream<Card.Rank> toRanks() {
        return this.cards().stream().map(Card::rank);
    }

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
        return new RuleSet(
            Rule.define(
                "Royal/Straight Flush",
                Hand::isStraightFlush,
                Hand::compareStraightFlushes
            ),
            Rule.define(
                "Four-of-a-Kind",
                Hand.isNOfAKind(4),
                Hand.compareNOfAKind(4)
            ),
            Rule.define(
                "Full House",
                Hand::isFullHouse,
                Hand::compareFullHouse
            ),
            Rule.define(
                "Flush",
                Hand::isFlush,
                Hand::comapareFlush
            ),
            Rule.define(
                "Straight",
                Hand::isStraight,
                Hand::compareStraightFlushes
            ),
            Rule.define(
                "Three-of-a-kind",
                Hand.isNOfAKind(3),
                Hand.compareNOfAKind(3)
            ),
            Rule.define(
                "Two Pair",
                Hand::isTwoPair,
                Hand::compareTwoPair
            ),
            Rule.define(
                "One Pair",
                Hand::isOnePair,
                Hand::compareOnePair
            ),
            Rule.define(
                "High Card",
                h -> true,
                Hand::comapreHighRanks
            )
        ).compare(this, other);
    }

    private int comapreHighRanks(Hand other) {
        return compareHighRanks(this.toRanks(), other.toRanks());
    }

    private static int compareHighRanks(Stream<Card.Rank> leftRanks, Stream<Card.Rank> rightRanks) {
        var comparisons = Streams.zip(
            leftRanks.sorted(Comparator.reverseOrder()),
            rightRanks.sorted(Comparator.reverseOrder()),
            (l, r) -> l.compareTo(r)
        );

        return comparisons.filter(c -> c != 0).findFirst().orElse(0);
    }


    private int comapareFlush(Hand other) {
        var leftIter = this.cards.stream().map(Card::rank).sorted(Comparator.reverseOrder()).iterator();
        var rightIter = other.cards.stream().map(Card::rank).sorted(Comparator.reverseOrder()).iterator();


        while (leftIter.hasNext()) {
            Card.Rank left = leftIter.next();
            Card.Rank right;
            try {
                right = rightIter.next();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException("PROVIDED HANDS DO NOT HAVE EQUAL AMOUNT OF CARDS");
            }

            if (left.compareTo(right) != 0) {
                return left.compareTo(right);
            }
        }

        return 0;
    }

    private boolean isTwoPair() {
        var iter = this.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2).iterator();

        if (!iter.hasNext()) {
            return false;
        }

        iter.next();
        return iter.hasNext();
    }

    private int compareTwoPair(Hand other) {
        var leftIter = this.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2)
            .map(List::getFirst)
            .map(Card::rank)
            .sorted(Comparator.reverseOrder()).toList();

        var rightIter = other.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2)
            .map(List::getFirst)
            .map(Card::rank)
            .sorted(Comparator.reverseOrder()).toList();

        var leftPair1 = leftIter.get(0);
        var leftPair2 = leftIter.get(1);

        var rightPair1 = rightIter.get(0);
        var rightPair2 = rightIter.get(1);

        if (leftPair1.compareTo(rightPair1) != 0) {
            return leftPair1.compareTo(rightPair1);
        } else if (leftPair2.compareTo(rightPair2) != 0) {
            return leftPair2.compareTo(rightPair2);
        }

        var left = this.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() != 2)
            .map(List::getFirst)
            .map(Card::rank)
            .sorted(Comparator.reverseOrder())
            .findFirst().get();

        var right = other.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() != 2)
            .map(List::getFirst)
            .map(Card::rank)
            .sorted(Comparator.reverseOrder())
            .findFirst().get();

        return left.compareTo(right);
    }

    private int compareOnePair(Hand other) {
        var pair1 = this.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2)
            .map(List::getFirst)
            .findFirst().get();

        var pair2 = other.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2)
            .map(List::getFirst)
            .findFirst().get();

        if (pair1.rank().compareTo(pair2.rank()) == 0) {
            var highRank1 = this.cards.stream()
                .filter(p -> !p.rank().equals(pair1.rank()))
                .map(Card::rank)
                .sorted(Comparator.reverseOrder());
            var highRank2 = other.cards.stream()
                .filter(p -> !p.rank().equals(pair2.rank()))
                .map(Card::rank)
                .sorted(Comparator.reverseOrder());
            return compareHighRanks(highRank1, highRank2);
        }

        return pair1.rank().compareTo(pair2.rank());
    }

    private boolean isOnePair() {
        var iter = this.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter(list -> list.size() == 2).iterator();

        boolean var = iter.hasNext();
        return var;
    }

    private boolean isFlush() {
        return cards.stream()
            .collect(groupingBy((Card::suit)))
            .values().stream()
            .anyMatch((list) -> (long) list.size() == 5);
    }

    private int compareFullHouse(Hand other) {
        var comp11 = this.threeOfAKindRank();
        var comp12 = other.threeOfAKindRank();

        if (Integer.compare(comp11, comp12) == 0) {
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

    private static BiFunction<Hand, Hand, Integer> compareNOfAKind(int n) {
        return (left, right) -> {
            var comp1 = NOfAKindRank(n).apply(left);
            var comp2 = NOfAKindRank(n).apply(right);

            var pair1 = left.cards.stream()
                .collect(groupingBy(Card::rank))
                .values().stream()
                .filter(list -> list.size() == n)
                .map(List::getFirst)
                .findFirst().get();

            var pair2 = right.cards.stream()
                .collect(groupingBy(Card::rank))
                .values().stream()
                .filter(list -> list.size() == n)
                .map(List::getFirst)
                .findFirst().get();

            if (Integer.compare(comp1, comp2) == 0) {
                var highRank1 = left.cards.stream()
                    .filter(p -> !p.rank().equals(pair1.rank()))
                    .map(Card::rank)
                    .sorted(Comparator.reverseOrder());
                var highRank2 = right.cards.stream()
                    .filter(p -> !p.rank().equals(pair2.rank()))
                    .map(Card::rank)
                    .sorted(Comparator.reverseOrder());

                return compareHighRanks(highRank1, highRank2);
            }

            return Integer.compare(comp1, comp2);
        };
    }

    private static Predicate<Hand> isNOfAKind(int n) {
        return (self) -> self.cards.stream()
            .collect(groupingBy((Card::rank)))
            .values().stream()
            .anyMatch((list) -> (long) list.size() == n);
    }

    private static Function<Hand, Integer> NOfAKindRank(int n) {
        return (self) -> self.cards.stream()
            .collect(groupingBy(Card::rank))
            .values().stream()
            .filter((list) -> (long) list.size() == n)
            .map((hand) -> hand.stream().map(Card::rank))
            .findFirst().orElse(Stream.empty())
            .findFirst().map(r -> r.ordinal() + n + 1).orElse(0);
    }

    private int compareStraightFlushes(Hand other) {
        return Integer.compare(straightRank(), other.straightRank());
    }

    private int straightRank() {
        return cards.stream()
            .map(Card::rank)
            .sorted()
            .toList().getLast().ordinal();
    }

    private boolean isStraight() {
        var thisStream = cards.stream().map(Card::rank).sorted().toList();

        var royalFlushStream = Arrays.stream(values())
            .dropWhile(rank -> !rank.equals(thisStream.get(0)))
            .limit(5);

        return Iterators.elementsEqual(
            thisStream.iterator(),
            royalFlushStream.iterator()
        );
    }

    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }
}
