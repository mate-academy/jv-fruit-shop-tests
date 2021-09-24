package core.db;

import core.model.FruitRecord;
import core.model.OperationType;
import core.service.strategy.OperationTypeStrategy;

public class StorageServiceImpl implements StorageService<FruitRecord, OperationTypeStrategy> {

    public FruitRecord put(FruitRecord record, OperationTypeStrategy strategy) {
        if (!Storage.stockStorage.containsKey(record.getFruit())) {
            Storage.stockStorage.put(record.getFruit(), record.getAmount());
        }
        String operationType = record.getOperationType();
        int previousAmount = Storage.stockStorage.get(record.getFruit());
        int amount = 0;
        amount = strategy.getHandle(OperationType
                            .operationTypeOfShortName(operationType))
                    .getUpdateAmount(record, previousAmount);
        Storage.stockStorage.put(record.getFruit(), amount);
        return record;
    }
}
