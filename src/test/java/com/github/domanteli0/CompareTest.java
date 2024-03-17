package com.github.domanteli0;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class CompareTest {
    @Test
    public void royalFlush() {
        isLessThan(Hand.parse("KH JS 8H AH QH"), Hand.parse("KH JH TH AH QH"));
    }

    @Test
    public void royalFlushLookAlike() {
        isLessThan(
            Hand.parse("KH JS TH AH QH"),
            Hand.parse("KH JH TH AH QH")
        );
    }

    @Test
    void straightFlush() {
        isLessThan(
            Hand.parse("9C JH TH KH QH"),
            Hand.parse("KS JS TS 9S QS")
        );
    }

    @Test
    void straightFlush_LessThanRoyalFlush() {
        isLessThan(
            Hand.parse("KH JH TH 9H QH"),
            Hand.parse("KH JH TH AH QH")
        );
    }

    @Test
    void straightFlush_LessThanHigherRankStraightFlush() {
        isLessThan(
            Hand.parse("8H JH TH 9H QH"),
            Hand.parse("KH JH TH 9H QH")
        );

    }

    // > The implementor must ensure sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y.
    // -- https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html#compareTo-T-
    //
    // In other words, if x < y, then it must also be true that y > x
    private static void isLessThan(Hand left, Hand right) {
        assertThat(left).is(lessThan(right));
        assertThat(right).is(greaterThan(left));
    }

    private static Condition<Hand> greaterThan(Hand hand) {
        return new Condition<Hand>(
            (other) -> other.compareTo(hand) > 0,
            String.format("left > right")
        );
    }

    private static Condition<Hand> lessThan(Hand hand) {
        return new Condition<Hand>(
            (other) -> other.compareTo(hand) < 0,
            String.format("left < right")
        );
    }

}