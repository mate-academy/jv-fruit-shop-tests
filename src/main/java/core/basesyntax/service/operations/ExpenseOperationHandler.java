package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;

public class ExpenseOperationHandler implements OperationHandler {
    @Override
    public void operate(String fruitName, Integer weight) {
        if (weight > Storage.fruits.get(fruitName)) {
            throw new RuntimeException("There isn't too mach quantity of " + fruitName);
        }
        Storage.fruits.replace(fruitName, Storage.fruits.get(fruitName) - weight);
    }
}
