package service.impl;

import db.Storage;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.FruitShopService;
import strategy.OperationStrategy;

public class FruitShopServiceImpl implements FruitShopService {
    private final OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        if (operationStrategy == null) {
            throw new RuntimeException("Argument can't be null");
        }
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Map<String, Integer> processData(List<FruitTransaction> parsed) {
        if (parsed == null) {
            throw new RuntimeException("Arguments can't be null");
        }
        parsed.forEach(t -> operationStrategy.get(t.getOperation()).handler(t));
        return Storage.getAll();
    }
}
