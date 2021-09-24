package core.basesyntax.service.activities;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;

public class SupplyHandler implements ActivityHandler {
    @Override
    public void apply(FruitRecord record) {
        if (Storage.fruitsQuantity.containsKey(record.getFruit())) {
            Storage.fruitsQuantity.merge(record.getFruit(),record.getAmount(),(Integer::sum));
        } else {
            Storage.fruitsQuantity.put(record.getFruit(), record.getAmount());
        }
    }
}
