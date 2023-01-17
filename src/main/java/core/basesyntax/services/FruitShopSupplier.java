package core.basesyntax.services;

import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Map;

public interface FruitShopSupplier {
    Map<String, Fruit> fillTheMap(List<String> listWithFruits);
}
