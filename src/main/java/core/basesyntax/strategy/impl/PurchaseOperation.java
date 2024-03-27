package core.basesyntax.strategy.impl;

import core.basesyntax.exceptions.InvalidFruitException;
import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.service.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    private final StorageService storageService;

    public PurchaseOperation(StorageService storage) {
        this.storageService = storage;
    }

    @Override
    public void handle(FruitsTransaction fruitsTransaction) {
        int currentQuantity = storageService.getQuantity(fruitsTransaction.getName());
        if (currentQuantity >= fruitsTransaction.getQuantity()) {
            storageService.updateQuantity(
                    fruitsTransaction.getName(), currentQuantity
                            - fruitsTransaction.getQuantity());
        } else {
            throw new InvalidFruitException("not enough fruit in storage");
        }
    }
}
