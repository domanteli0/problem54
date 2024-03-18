package com.github.domanteli0;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface Rule {
    static Rule define(String ruleName, Predicate<Hand> check, BiFunction<Hand, Hand, Integer> compare) {
        return new Rule() {
            @SuppressWarnings({"java:S1170", "unused"})
            // Useful then debugging
            public final String name = ruleName;

            public boolean check(Hand hand) {
                return check.test(hand);
            }

            public int compare(Hand left, Hand right) {
                return compare.apply(left, right);
            }
        };
    }

    boolean check(Hand hand);

    int compare(Hand left, Hand right);
}
