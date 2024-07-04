package core.basesyntax.service.strategy.strategyimpl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;

public class BalanceStrategy implements TypeService {
    @Override
    public void calculation(FruitRecord record) {
        Storage.storage.put(record.getFruit(), record.getQuantity());
    }
}
