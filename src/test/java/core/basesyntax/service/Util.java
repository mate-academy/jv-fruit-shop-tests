package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static final List<String> INPUT_FILE_LINES = List.of("type,fruit,quantity",
            "b,banana,20",
            "p,apple,10",
            "s,apple,15",
            "p,apple,5",
            "r,lemon,50",
            "p,lemon,20");
    public static final List<String> REPORT = List.of("fruit,quantity",
            "banana,50",
            "apple,65",
            "lemon,80");
    public static final Fruit banana = new Fruit("banana");
    public static final Fruit apple = new Fruit("apple");
    public static final Fruit lemon = new Fruit("lemon");
    private static final String DIRECTORY_PATH = "src/test/resources";

    public static String createTextFromLines(List<String> lines) {
        return lines.stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static void createNewFolder() {
        new File(DIRECTORY_PATH).mkdir();
    }
}
