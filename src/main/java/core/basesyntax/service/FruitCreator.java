package core.basesyntax.service;

import core.basesyntax.model.Fruit;

public interface FruitCreator {
    Fruit createFruit(String fruitName, String amount);
}
