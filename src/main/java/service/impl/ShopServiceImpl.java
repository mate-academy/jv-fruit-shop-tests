package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import service.ShopService;
import storage.Storage;
import strategy.OperationStrategy;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactionList) {
        if (fruitTransactionList == null) {
            throw new RuntimeException(
                    "FruitTransactionList cannot be null");
        }
        for (FruitTransaction fruitTransaction : fruitTransactionList) {
            if (fruitTransaction == null) {
                throw new RuntimeException(
                        "FruitTransaction object cannot be null");
            }
        }
        Map<String, Integer> fruitStock = new HashMap<>();

        for (FruitTransaction fruitTransaction : fruitTransactionList) {
            String fruitName = fruitTransaction.getFruit();
            if (fruitName == null) {
                throw new RuntimeException(
                        "Fruit name cannot be null");
            }
            int currentAmount = fruitStock.getOrDefault(fruitName, 0);

            int updatedAmount = operationStrategy.applyOperation(fruitTransaction, currentAmount);
            fruitStock.put(fruitName, updatedAmount);
        }
        Storage.getFruitDbResult().putAll(fruitStock);
    }
}
