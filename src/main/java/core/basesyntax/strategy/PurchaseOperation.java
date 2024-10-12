package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;

public class PurchaseOperation implements OperationHandler {
    public static final String READ_FILE_PATH = "src/main/resources/reportToRead.csv";
    private final Storage storage;

    public PurchaseOperation(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void handleTransaction(FruitTransaction fruitTransaction) {
        int newQuantity = storage.getQuantity(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();
        if (fruitTransaction.getQuantity() > 0 && newQuantity >= 0) { //purchase operation cannot accept a negative balance as input and cannot be greater than the number of available fruits in storage
            storage.put(fruitTransaction.getFruit(), newQuantity);
        } else {
            throw new RuntimeException("negative balance " + newQuantity
                    + " cannot be recorded at "
                    + PurchaseOperation.class + " from fail " + READ_FILE_PATH);
        }
    }
}
