package core.basesyntax.operationtype;

import static core.basesyntax.db.Storage.fruitQuantity;

import core.basesyntax.model.FruitRecord;

public class BalanceHandler implements OperationHandler {

    @Override
    public void apply(FruitRecord fruitRecord) {
        fruitQuantity.put(fruitRecord.getFruit(), fruitRecord.getAmount());
    }
}
