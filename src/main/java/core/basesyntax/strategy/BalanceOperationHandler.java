package core.basesyntax.strategy;

import core.basesyntax.service.Storage;
import java.util.Map;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
        Storage.updateInventory(fruit, quantity);
    }
}
