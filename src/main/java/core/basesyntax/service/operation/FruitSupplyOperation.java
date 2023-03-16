package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperation;

public class FruitSupplyOperation implements FruitOperation {
    @Override
    public void fruitOperate(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() > 0) {
            Storage.fruitStorage.put(fruitTransaction.getFruit(),
                    Storage.fruitStorage
                            .get(fruitTransaction.getFruit()) + fruitTransaction.getQuantity());
            return;
        }
        throw new RuntimeException("Fruit quantity for supply should be > 0");
    }
}
