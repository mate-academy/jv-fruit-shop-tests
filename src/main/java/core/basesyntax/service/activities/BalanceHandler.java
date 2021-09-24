package core.basesyntax.service.activities;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;

public class BalanceHandler implements ActivityHandler {
    @Override
    public void apply(FruitRecord record) {
        if (Storage.fruitsQuantity.containsKey(record.getFruit())) {
            Storage.fruitsQuantity.replace(record.getFruit(),record.getAmount());
        } else {
            Storage.fruitsQuantity.put(record.getFruit(), record.getAmount());
        }
    }
}
