package core.basesyntax.service.activities;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;

public class PurchaseHandler implements ActivityHandler {
    @Override
    public void apply(FruitRecord record) {
        Integer currentBalance;
        if (Storage.FRUITS_QUANTITY.containsKey(record.getFruit())) {
            currentBalance = Storage.FRUITS_QUANTITY.get(record.getFruit());
        } else {
            throw new RuntimeException("Invalid key, " + record.getFruit());
        }
        if (currentBalance >= record.getAmount()) {
            Storage.FRUITS_QUANTITY.merge(record.getFruit(),record.getAmount(),(v1, v2) -> v1 - v2);
        } else {
            throw new RuntimeException("Operation Purchase cannot be performed with this data: "
                    + record.getAmount());
        }
    }
}
