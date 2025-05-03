package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ErrorDataException;

public class SupplyOperationStrategy implements OperationStrategy {
    private Storage storage;

    public SupplyOperationStrategy(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void process(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new ErrorDataException("Supply quantity couldn't be less than 0");
        } else {
            storage.updateFruitQuantity(transaction.getFruit(),
                    storage.getFruitQuantity(transaction.getFruit()) + transaction.getQuantity());
        }
    }
}
