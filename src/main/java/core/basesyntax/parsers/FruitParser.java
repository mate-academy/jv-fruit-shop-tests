package core.basesyntax.parsers;

import core.basesyntax.model.Fruit;

public interface FruitParser {
    Fruit parse(String fruitName);
}
