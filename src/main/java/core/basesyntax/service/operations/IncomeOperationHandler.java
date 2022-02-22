package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;

public class IncomeOperationHandler implements OperationHandler {
    @Override
    public void operate(String fruitName, Integer weight) {
        if (fruitName == null || weight == null) {
            throw new RuntimeException("Adding to Storage data are absent.");
        }
        if (Storage.fruits.containsKey(fruitName)) {
            Storage.fruits.replace(fruitName, Storage.fruits.get(fruitName) + weight);
        } else {
            Storage.fruits.put(fruitName, weight);
        }
    }
}
