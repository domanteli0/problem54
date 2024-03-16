package com.github.domanteli0;

public record Card(Rank rank, Suit suit) {
    public enum Suit {Spade, Club, Diamond, Heart}

    public enum Rank {_2, _3, _4, _5, _6, _7, _8, _9, _10, Jack, Queen, King, Ace}

    // TODO: validate `input` length
    public static Card parse(String input) throws IllegalArgumentException {
        char first = input.charAt(0);
        char second = input.charAt(1);

        Card card = new Card(matchRank(first), matchSuit(second));


        return card;
    }

    private static Suit matchSuit(char value) {
        return switch (value) {
            case 'S' -> Suit.Spade;
            case 'C' -> Suit.Club;
            case 'D' -> Suit.Diamond;
            case 'H' -> Suit.Heart;
            default -> throw new IllegalArgumentException(makeIllegalSuitMessage(value));
        };
    }

    private static Rank matchRank(char value) {
        return switch (value) {
            case '2' -> Rank._2;
            case '3' -> Rank._3;
            case '4' -> Rank._4;
            case '5' -> Rank._5;
            case '6' -> Rank._6;
            case '7' -> Rank._7;
            case '8' -> Rank._8;
            case '9' -> Rank._9;
            case 'T' -> Rank._10;
            case 'J' -> Rank.Jack;
            case 'Q' -> Rank.Queen;
            case 'K' -> Rank.King;
            case 'A' -> Rank.Ace;
            default -> throw new IllegalArgumentException(makeIllegalRankMessage(value));
        };
    }

    // TODO: custom exception type
    private static String makeIllegalSuitMessage(char value) {
        return String.format(
            "Invalid character '%c' encountered, expected a character one of %s",
            value,
            "'S, 'C', 'D', 'H'"
        );
    }

    private static String makeIllegalRankMessage(char value) {
        return String.format(
            "Invalid character '%c' encountered, expected a character one of %s",
            value,
            "'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'"
        );
    }
}
