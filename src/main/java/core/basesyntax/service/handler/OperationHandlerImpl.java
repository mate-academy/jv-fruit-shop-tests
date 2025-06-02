package core.basesyntax.service.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;

public class OperationHandlerImpl implements OperationHandlers {
    @Override
    public void handle(FruitTransaction transaction) {
        FruitTransaction.Operation operation = transaction.getOperation();
        if (operation == null) {
            throw new UnsupportedOperationException("Operation is null");
        }

        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        int currentQuantity = Storage.fruitStorage.getOrDefault(fruit, 0);
        int updatedQuantity;

        switch (operation) {
            case BALANCE:
                updatedQuantity = quantity;
                break;
            case SUPPLY:
            case RETURN:
                updatedQuantity = currentQuantity + quantity;
                break;
            case PURCHASE:
                updatedQuantity = currentQuantity - quantity;
                if (updatedQuantity < 0) {
                    throw new IllegalArgumentException("Not enough stock for purchase of " + fruit);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported operation: " + operation);
        }

        Storage.fruitStorage.put(fruit, updatedQuantity);
    }
}
