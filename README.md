# problem54

Solution of [Project Euler's 54th](https://projecteuler.net/problem=54) problem using Java.

## Building

This project uses `gradle` as it's build tool.
To run the project simply `gradle run --args='poker.txt'`, to run tests: `gradle test`.

## About the project

### How the solution works

1. Parse each hand.
2. Compare hands, by finding a rule where player 1's hand matches, such as "Four of a Kind",
   if the other player's hand doesn't match,
   then that means that the first hand is valued higher.
   Otherwise, find out which hands' rank is higher.

3. Count all hands where player 1 wins.

Some additional explanations are located in javadocs of `Rule` and `RuleSet`.

### What I like and do not like about my solution

*What I like:*

* It's not over-engineered (at least I think so),
  but at the same time the concerns of each rule are separated.

* It's quite functional

*What I don't like:*

* All the `TODO`s which were left unfinished.
  If I was willing to spend more time, I'd finish them.

* Error handling, or rather error reporting, could be improved, by:
    - checking that there is only 5 cards in a hand.
    - checking that each substring is only two characters long.
    - continue parsing other cards (possibly reporting more errors) even when parsing of a substring fails.

### Which of the technologies or approaches were new to me

* JUnit & AssertJ.

  Although, I've used similar test frameworks before,
  it took some time to figure out the specifics of these.

* Java `Stream`s

  Coming form such languages as Haskell, Rust, Scala,
  Java's `Stream` feels somewhat esoteric.
  Having the solution in your head,
  but unable to translate it to Java, at times, was a bit frustrating.
