package core.basesyntax.strategy.handlers;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.Warehouse;

public class OperationSupply implements OperationHandler {
    @Override
    public int warehouseOperation(String fruit, int quantity, Warehouse warehouse) {
        if (fruit == null || warehouse == null) {
            throw new NullPointerException("There is no empty data accepted");
        }
        int remainingQuantity = warehouse.getRemains().computeIfAbsent(fruit, s -> 0);
        warehouse.getDayOperations().add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, quantity));
        warehouse.getRemains().put(fruit, remainingQuantity + quantity);
        return warehouse.getRemains().get(fruit);
    }
}
