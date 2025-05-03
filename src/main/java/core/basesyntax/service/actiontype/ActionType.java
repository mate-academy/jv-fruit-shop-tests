package core.basesyntax.service.actiontype;

import core.basesyntax.fruit.FruitTransaction;

public interface ActionType {
    int getNewValue(FruitTransaction fruitTransaction);
}
