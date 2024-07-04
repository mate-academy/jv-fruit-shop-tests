package core.basesyntax.service.strategy.strategyimpl;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitRecord;

public class SupplyStrategy implements TypeService {
    @Override
    public void calculation(FruitRecord record) {
        int supplyQuantity = record.getQuantity();
        Storage.storage.merge(record.getFruit(), supplyQuantity, Integer::sum);
    }
}
