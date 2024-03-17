package com.github.domanteli0;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface Rule {
    public static Rule define(Predicate<Hand> check, BiFunction<Hand, Hand, Integer> compare) {
        return new Rule() {
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
