package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> storage) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (storage == null) {
            throw new IllegalArgumentException("Storage cannot be null");
        }

        int currentQuantity = storage.getOrDefault(transaction.getFruit(), 0);
        int newQuantity = currentQuantity - transaction.getQuantity();
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Attempt to purchase more "
                    + transaction.getFruit() + " than available.");
        }

        storage.put(transaction.getFruit(), newQuantity);
    }
}
