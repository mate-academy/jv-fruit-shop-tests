package mate.academy.fruitshop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mate.academy.fruitshop.model.FruitTransaction;
import mate.academy.fruitshop.service.OperationStrategy;
import mate.academy.fruitshop.service.ShopService;

public class ShopServiceImpl implements ShopService {

    private final Map<String, Integer> storage = new HashMap<>();
    private final OperationStrategy strategy;

    public ShopServiceImpl(OperationStrategy strategy) {
        if (strategy == null) {
            throw new RuntimeException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transactions cannot be null");
        }

        for (FruitTransaction tx : transactions) {
            if (tx == null) {
                continue;
            }

            strategy.get(tx.getOperation()).process(tx, storage);
        }
    }

    @Override
    public Map<String, Integer> getStorage() {
        return new HashMap<>(storage);
    }
}
