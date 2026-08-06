package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class SupplyOperation implements OperationHandler {
    @Override
    public void operate(Map<String, Integer> fruits, FruitTransaction transaction) {
        int currentQuantity = Storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        int newQuantity = currentQuantity + transaction.getQuantity();
        Storage.getFruits().put(transaction.getFruit(), newQuantity);
    }
}
