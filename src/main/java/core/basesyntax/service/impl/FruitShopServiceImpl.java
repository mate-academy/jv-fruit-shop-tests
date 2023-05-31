package core.basesyntax.service.impl;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShopServiceImpl implements FruitShopService {
    private final OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        if (operationStrategy == null) {
            throw new RuntimeException();
        }
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Map<String, Integer> finalReport(List<FruitTransaction> parsed) {
        if (parsed == null) {
            throw new RuntimeException("None of the arguments must be null");
        }
        if (parsed.isEmpty()) {
            return new HashMap<>();
        }
        parsed.forEach(t -> operationStrategy.get(t.getOperation()).operate(t));
        return Storage.getMap();
    }
}
