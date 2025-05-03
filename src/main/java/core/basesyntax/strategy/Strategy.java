package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface Strategy {

    FruitHandler choosePattern(FruitTransaction fruitTransaction);

}
