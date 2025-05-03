package core.basesyntax.strategy;

import core.basesyntax.service.StorageService;

public class BalanceHandler implements FruitShopOperationsHandler {
    private StorageService storageService;

    public BalanceHandler(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void applyOperation(String product, int quantity) {
        if (product == null) {
            throw new RuntimeException("Product name can't be null");
        }
        storageService.add(product, quantity);
    }
}
