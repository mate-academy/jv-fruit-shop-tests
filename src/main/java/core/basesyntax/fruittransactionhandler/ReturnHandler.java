package core.basesyntax.fruittransactionhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandler implements OperationHandler {

    private Storage storageService;

    public ReturnHandler(Storage storageService) {
        this.storageService = storageService;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("The input transaction is null.");
        }
        storageService.getDataStorageMapOrDefault(fruitTransaction.getFruit())
                .compute(fruitTransaction.getFruit(), (k, currentValue)
                        -> (currentValue == null ? 0 : currentValue)
                        + fruitTransaction.getQuantity());
    }
}
