package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;

public class BalanceHandlerImpl implements Handler {

    @Override
    public int changeAmount(FruitRecord fruitRecord) {
        Storage.storage.put(fruitRecord.getFruit(), fruitRecord.getAmount());
        return fruitRecord.getAmount();
    }

}
