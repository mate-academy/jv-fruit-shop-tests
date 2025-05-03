package core.basesyntax.service.activities;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;

public class BalanceHandler implements ActivityHandler {
    @Override
    public void apply(FruitRecord record) {
        if (Storage.FRUITS_QUANTITY.containsKey(record.getFruit())) {
            Storage.FRUITS_QUANTITY.replace(record.getFruit(),record.getAmount());
        } else {
            Storage.FRUITS_QUANTITY.put(record.getFruit(), record.getAmount());
        }
    }
}
