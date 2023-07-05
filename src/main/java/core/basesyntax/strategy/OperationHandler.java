package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void doActivity(FruitTransaction transaction, ShopStorage fruitStorage);
}
