package core.basesyntax.service.strategy.strategyimpl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;

public class ReturnStrategy implements TypeService {
    @Override
    public void calculation(FruitRecord record) {
        int returnFruits = record.getQuantity();
        Storage.storage.merge(record.getFruit(),returnFruits, Integer::sum);
    }
}
