package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class BalanceOperation implements OperationHandler {
    @Override
    public void operate(Map<String, Integer> fruits, FruitTransaction transaction) {
        Storage.getFruits().put(transaction.getFruit(), transaction.getQuantity());
    }
}
