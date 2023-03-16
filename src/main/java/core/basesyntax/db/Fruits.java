package core.basesyntax.db;

import java.util.List;

public class Fruits {
    private static final List<String> fruits = List.of("banana", "apple", "orange");

    public static String getFruit(String line) {
        for (String audit : fruits) {
            if (line.contains(audit)) {
                return audit;
            }
        }
        throw new RuntimeException("Does not valid fruit");
    }
}
