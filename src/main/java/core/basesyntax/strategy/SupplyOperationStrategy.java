package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;

public class SupplyOperationStrategy implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getOperation() == null
                || transaction.getFruit() == null
                || transaction.getFruit().equals("")) {
            throw new RuntimeException("Wrong input data. "
                    + "Fields of FruitTransactions can't be null or empty");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Wrong input data. "
                    + "Supply operation quantity can't be negative");
        }
        if (!Storage.getFruitStorage().containsKey(transaction.getFruit())) {
            Storage.getFruitStorage().put(transaction.getFruit(), transaction.getQuantity());
        } else {
            Storage.getFruitStorage().replace(transaction.getFruit(), transaction.getQuantity()
                    + Storage.getFruitStorage().get(transaction.getFruit()));
        }
    }
}
