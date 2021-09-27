package core.basesyntax.service.activities;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;

public class SupplyHandler implements ActivityHandler {
    @Override
    public void apply(FruitRecord record) {
        if (Storage.FRUITS_QUANTITY.containsKey(record.getFruit())) {
            Storage.FRUITS_QUANTITY.merge(record.getFruit(),record.getAmount(),(Integer::sum));
        } else {
            Storage.FRUITS_QUANTITY.put(record.getFruit(), record.getAmount());
        }
    }
}
