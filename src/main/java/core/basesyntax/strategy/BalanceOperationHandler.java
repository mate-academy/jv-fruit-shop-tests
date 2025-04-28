package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.inventory;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        System.out.println("Current balance of " + fruit + ": " + inventory.getOrDefault(fruit, 0));
    }
}
