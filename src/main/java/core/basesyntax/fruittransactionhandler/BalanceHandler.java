package core.basesyntax.fruittransactionhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {

    private Storage storageService;

    public BalanceHandler(Storage storageService) {
        this.storageService = storageService;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("Input transaction is null.");
        }
        storageService.getDataStorageMapOrDefault(fruitTransaction.getFruit())
                .put(fruitTransaction.getFruit(),
                    fruitTransaction.getQuantity());
    }
}
