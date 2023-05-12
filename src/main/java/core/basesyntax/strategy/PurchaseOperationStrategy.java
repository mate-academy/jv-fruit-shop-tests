package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitTransaction;

public class PurchaseOperationStrategy implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getOperation() == null
                || transaction.getFruit() == null
                || transaction.getFruit().equals("")) {
            throw new RuntimeException("Wrong input data. "
                    + "Fields of FruitTransactions can't be null or empty");
        }
        if (!Storage.getFruitStorage().containsKey(transaction.getFruit())) {
            throw new RuntimeException("Wrong input data. "
                    + "This fruit is missing in this fruit shop");
        }
        if (transaction.getQuantity() > Storage.getFruitStorage().get(transaction.getFruit())) {
            throw new RuntimeException("Wrong input data. "
                    + "You can't purchase more than fruit shop have in storage");
        }
        Storage.getFruitStorage().replace(transaction.getFruit(),
                Storage.getFruitStorage().get(transaction.getFruit())
                - transaction.getQuantity());
    }
}
