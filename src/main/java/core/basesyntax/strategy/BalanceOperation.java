package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;

public class BalanceOperation implements OperationHandler {
    public static final String READ_FILE_PATH = "src/main/resources/reportToRead.csv";
    private Storage storage;

    public BalanceOperation(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void handleTransaction(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() >= 0) {
            storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        } else {
            throw new RuntimeException("negative balance " + fruitTransaction.getQuantity()
                    + " cannot be recorded at "
                    + BalanceOperation.class + " from fail " + READ_FILE_PATH);
        }
    }
}
