package core.basesyntax.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;

public class ShopServiceImpl implements ShopService {
    private final Map<FruitTransaction.Operation, OperationHandler> handlers;
    private final Map<String, Integer> storage = new HashMap<>();

    public ShopServiceImpl(Map<FruitTransaction.Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = handlers.get(transaction.getOperation());
            handler.apply(transaction, storage);
        }
    }

    @Override
    public Map<String, Integer> getStorage() {
        return Collections.unmodifiableMap(storage);
    }

    public void addFruit(String fruit, int quantity) {
        storage.put(fruit, storage.getOrDefault(fruit, 0) + quantity);
    }

    public int getFruitQuantity(String fruit) {
        return storage.getOrDefault(fruit, 0);
    }

    public void removeFruit(String fruit, int quantity) {
        int currentQuantity = storage.getOrDefault(fruit, 0);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Niewystarczająca ilość owoców: " + fruit);
        }
        storage.put(fruit, currentQuantity - quantity);
    }
}
