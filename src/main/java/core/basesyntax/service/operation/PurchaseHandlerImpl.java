package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import java.util.Map;

public class PurchaseHandlerImpl implements Handler {
    @Override
    public int changeAmount(FruitRecord fruitRecord) {
        int newAmount = 0;
        for (Map.Entry<Fruit, Integer> fruitEntry : Storage.storage.entrySet()) {
            if (fruitEntry.getKey().equals(fruitRecord.getFruit())) {
                newAmount = fruitEntry.getValue() - fruitRecord.getAmount();
            }
        }
        Storage.storage.put(fruitRecord.getFruit(), newAmount);
        return newAmount;
    }
}
