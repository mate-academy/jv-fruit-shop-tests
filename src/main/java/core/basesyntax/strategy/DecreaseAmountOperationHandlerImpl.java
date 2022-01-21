package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;

public class DecreaseAmountOperationHandlerImpl implements OperationHandler {
    @Override
    public int getChangedAmount(FruitRecord record) {
        int newAmount = Storage.getStorage().get(record.getFruit()) - record.getAmount();
        if (newAmount < 0) {
            throw new RuntimeException(
                    "Can't purchase because of there is not enough fruits in storage.");
        }
        Storage.getStorage().put(record.getFruit(), newAmount);
        return newAmount;
    }
}
