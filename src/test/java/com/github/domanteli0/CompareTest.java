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

    @Test
    void fourOfAKind() {
        isLessThan(
            Hand.parse("KS JH TS 9S QD"),
            Hand.parse("9C 9S QH 9H, 9D")
        );
    }

    @Test
    void fourOfAKind_LessThanStraightFlush() {
        isLessThan(
            Hand.parse("9C 9S QH 9H 9D"),
            Hand.parse("8H JH TH 9H QH")
        );
    }

    @Test
    void fourOfAKind_LessThanHigherKinkFourOfAKind() {
        isLessThan(
            Hand.parse("8C 8S QH 8H 8D"),
            Hand.parse("9C 9S QH 9H 9D")
        );
    }

    @Test
    void fourOfAKind_LessThanEqualKinkFourOfAKindWithHigherRankLeftover() {
        isLessThan(
            Hand.parse("9C 9S TH 9H 9D"),
            Hand.parse("9C 9S KD 9H 9D")
        );
    }

    @Test
    void fullHouse() {
        isLessThan(
            Hand.parse("KS 7H TS 9C 3D"),
            Hand.parse("AH AD AS KH KD")
        );
    }

    @Test
    void fullHouse_LessThanFourOfAKind() {
        isLessThan(
            Hand.parse("AH AD AS KH KD"),
            Hand.parse("9C 9S KD 9H 9D")
        );
    }

    @Test
    void fullHouse_LessThanFullHouseWithHigherThreeOfAKind() {
        isLessThan(
            Hand.parse("9H 9D 9S KH KD"),
            Hand.parse("KH KD KS AH AD")
        );
    }

    @Test
    void fullHouse_LessThanFullHouseWithHigherPair() {
        isLessThan(
            Hand.parse("9H 9D 9S QH QD"),
            Hand.parse("9H 9D 9S KH KD")
        );
    }

    @Test
    void flush() {
        isLessThan(
            Hand.parse("KS 7H TS 9C 3D"),
            Hand.parse("KS 7S TS 9S 3S")
        );
    }

    @Test
    void flush_LessThanFullHouse() {
        isLessThan(
            Hand.parse("KS 7S TS 9S 3S"),
            Hand.parse("AH AD AS KH KD")
        );
    }

    @Test
    void straight_LessThanStraight() {
        isLessThan(
            Hand.parse("KH 7H TH 9H 3H"),
            Hand.parse("KS 5S 7S 8S QS")
        );
    }

    @Test
    void flush_LessThanFlushWithFourCardsEqual_LowEnd() {
        isLessThan(
            Hand.parse("KS 7S TS 9S 2S"),
            Hand.parse("KH 3H 7H TH 9H")
        );
    }

    @Test
    void flush_LessThanFlushWithFourCardsEqual_HighEnd() {
        isLessThan(
            Hand.parse("QC 7C TC 9C 3C"),
            Hand.parse("KS 7S TS 9S 3S")
        );
    }

    @Test
    void flush_LessThanFlushWithFourCardsEqual_Middle() {
        isLessThan(
            Hand.parse("TD KD 7D 9D 3D"),
            Hand.parse("KH 7H 9H 3H QH")
        );
    }

    @Test
    void straight() {
        isLessThan(
            Hand.parse("KS 7H TS 9C 3D"),
            Hand.parse("2C, 3H, 4H, 5S, 6D")
        );
    }

    @Test
    void straight_LessThanLowerStraight() {
        isLessThan(
            Hand.parse("2C, 3H, 4H, 5S, 6D"),
            Hand.parse("7C, 3H, 4H, 5S, 6D")
        );
    }

    @Test
    void threeOfAKind() {
        isLessThan(
            Hand.parse("KS 7H TS 9C 3D"),
            Hand.parse("TH TC AC 8S TD")
        );
    }

    @Test
    void threeOfAKind_LessThanStraight() {
        isLessThan(
            Hand.parse("TH TC 3C KS TD"),
            Hand.parse("2C, 3H, 4H, 5S, 6D")
        );
    }

    @Test
    void threeOfAKind_LessThan_HigherThreeOfAKind() {
        isLessThan(
            Hand.parse("3H 3C AD 7D 3D"),
            Hand.parse("9H 9C KS 8C 9D")
        );
    }

    @Test
    void threeOfAKind_LessThan_EqualThreeOfAKind() {
        isLessThan(
            Hand.parse("3H 3C AD 7D 3D"),
            Hand.parse("3H 3C AD 8D 3D")
        );

    }

    @Test
    void twoPair_LessThan_ThreeOfAKind() {
        isLessThan(
            Hand.parse("6S 6D QC QH 3D"),
            Hand.parse("2C, 3H, 4H, 5S, 6D")
        );
    }

    @Test
    void twoPair_LessThan_HigherTwoPair1() {
        isLessThan(
            Hand.parse("6S 6D QC QH 3D"),
            Hand.parse("7S 7D QC QH 3D")
        );
    }

    @Test
    void twoPair_LessThan_HigherTwoPair2() {
        isLessThan(
            Hand.parse("7S 7D QC QH 3D"),
            Hand.parse("7S 7D KC KH 3D")
        );
    }

    @Test
    void twoPair_LessThan_EqualTwoPair() {
        isLessThan(
            Hand.parse("7S 7D KC KH 3D"),
            Hand.parse("7S 7D KC KH 4D")
        );
    }

    @Test
    void onePair_LessThan_twoPair() {
        isLessThan(
            Hand.parse("5S 3H 3C 7D 9C"),
            Hand.parse("6S 6D QC QH 3D")
        );
    }

    @Test
    void onePair_LessThan_HigherOnePair() {
        isLessThan(
            Hand.parse("5S 3H 3C 7D 9C"),
            Hand.parse("5S 7D 8H 8C 9S")
        );
    }

    @Test
    void onePair_LessThan_EqualOnePair() {
        isLessThan(
            Hand.parse("5S 3H 3C 7D 9C"),
            Hand.parse("6S 3C 7D 9C 3H")
        );
    }

    @Test
    void highCard_LessThan_onePair() {
        isLessThan(
            Hand.parse("KD, 7S 4S 3H JC"),
            Hand.parse("5S 3H 3C 7D 9C")
        );
    }

    @Test
    void highCard_LessThan_highCard1() {
        isLessThan(
            Hand.parse("QD, 7S 4S 3H JC"),
            Hand.parse("KD, 7S 4S 3H JC")
        );
    }

    @Test
    void highCard_LessThan_highCard2() {
        isLessThan(
            Hand.parse("QD, 7S 4S 2H JC"),
            Hand.parse("QD, 7S 4S 3H JC")
        );
    }

    // TODO: Test with random card order

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
            "left > right"
        );
    }

    private static Condition<Hand> lessThan(Hand hand) {
        return new Condition<Hand>(
            (other) -> other.compareTo(hand) < 0,
            "left < right"
        );
    }

}