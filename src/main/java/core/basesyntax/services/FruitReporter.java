package core.basesyntax.services;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface FruitReporter {
    String report(Map<Fruit, Integer> fruitsStorage);
}
