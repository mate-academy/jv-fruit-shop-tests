package core.basesyntax.testservice;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomDataGenerator {
    public String generateRandomData() {
        Random random = new Random();
        return random.ints('a', 'z' + 1)
                .limit(5)
                .mapToObj(letter -> String.valueOf((char) letter))
                .collect(Collectors.joining());
    }
}
