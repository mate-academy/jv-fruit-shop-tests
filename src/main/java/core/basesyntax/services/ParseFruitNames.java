package core.basesyntax.services;

import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Map;

public interface ParseFruitNames {
    Map<String, Fruit> getFruitNamesMap(List<String> listOfAllFruits);
}
