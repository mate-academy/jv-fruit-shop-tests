package core.basesyntax.operationtype;

import static core.basesyntax.db.Storage.fruitQuantity;

import core.basesyntax.model.FruitRecord;

public class PurchaseHandler implements OperationHandler {

    @Override
    public void apply(FruitRecord fruitRecord) {
        Integer fruitInStorage = fruitQuantity.get(fruitRecord.getFruit());
        fruitInStorage -= fruitRecord.getAmount();
        fruitQuantity.put(fruitRecord.getFruit(), fruitInStorage);
        if (fruitInStorage < 0) {
            throw new RuntimeException("Not enough fruit in storage.");
        }
    }
}
