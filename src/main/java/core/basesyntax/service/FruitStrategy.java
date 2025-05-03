package core.basesyntax.service;

import core.basesyntax.strategy.FruitOperation;

public interface FruitStrategy {
    FruitOperation makeOperation(String abbreviation);
}
