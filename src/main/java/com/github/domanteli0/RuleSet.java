package com.github.domanteli0;

record RuleSet(Rule... rules) {
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

