package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {

    @Override
    public void processWithTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("empty input parameters");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("invalid empty parameters");
        }
        if (Storage.getFruits().containsKey(transaction.getFruit())) {
            int quantityFromStorage = Storage.getFruits().get(transaction.getFruit());
            int quantity = quantityFromStorage + transaction.getQuantity();
            Storage.getFruits().put(transaction.getFruit(), quantity);
        } else {
            Storage.getFruits().put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
