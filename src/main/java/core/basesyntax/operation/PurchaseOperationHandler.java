package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import java.util.Map;

public class PurchaseOperationHandler implements OperationHandler {
    private final StorageService storageService;

    public PurchaseOperationHandler(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> storage) {
        String fruit = transaction.getFruit();
        int amount = transaction.getAmount();
        storageService.purchaseFruit(fruit, amount);
    }
}
