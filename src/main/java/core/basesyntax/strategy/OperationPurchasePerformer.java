package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class OperationPurchasePerformer implements OperationPerformer {
    @Override
    public void perform(FruitTransaction transaction) {
        int numberAfterSelling = Storage.getFruits().get(transaction.getFruit())
                - transaction.getQuantity();
        if (numberAfterSelling < 0) {
            throw new RuntimeException("You haven`t fruits to sell.");
        } else {
            Storage.getFruits().compute(transaction.getFruit(),
                    (key, val) -> val - transaction.getQuantity());
        }
    }
}
