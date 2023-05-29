package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceTransaction implements CalculationService {
    @Override
    public void calculateAndStore(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be a negative value!!");
        }
        Storage.STORAGE.put(transaction.getFruit(), transaction.getQuantity());
    }
}
