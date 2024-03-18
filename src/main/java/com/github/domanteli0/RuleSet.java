package com.github.domanteli0;


/**
 * Contains rules by which order is determined.
 *
 * @see RuleSet#compare(Hand, Hand) compare
 */
record RuleSet(Rule... rules) {
    /**
     * Compares {@link com.github.domanteli0.Hand}s by checking which rule applies first to which hand, in the order
     * they were declared.
     * <p>
     * If both {@link com.github.domanteli0.Hand}s pass the check, {@link Rule#compare(Hand, Hand)} is invoked to
     * determine order.
     */
    public int compare(Hand left, Hand right) {

        for (var rule : rules) {
            var leftCheck = rule.check(left);
            var rightCheck = rule.check(right);

            if (leftCheck && rightCheck) {
                return rule.compare(left, right);
            } else if (leftCheck) {
                return 1;
            } else if (rightCheck) {
                return -1;
            }
        }

        throw new UnsupportedOperationException("NOT ALL CASES COVERED BY RULES");
    }
}

