package com.github.domanteli0;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(String.format(
            "First player won %s times",
            calculateFirstPlayerWinsFromFile(args[0])
        ));
    }

    public static int calculateFirstPlayerWinsFromFile(String filePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.map(
                line -> {
                    var leftStr = line.substring(0, 14);
                    var rightStr = line.substring(15, 29);

                    var leftHand = Hand.parse(leftStr);
                    var rightHand = Hand.parse(rightStr);
                    return (leftHand.compareTo(rightHand) < 0) ? 0 : 1;
                }
            ).reduce(0, (a, b) -> a + b);
        }
    }
}