package core.basesyntax.strategy;

import core.basesyntax.service.StorageService;

public class PurchaseHandler implements FruitShopOperationsHandler {
    private StorageService storageService;

    public PurchaseHandler(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void applyOperation(String product, int quantity) {
        if (product == null) {
            throw new RuntimeException("Product name can't be null");
        }
        if (quantity < 0) {
            throw new RuntimeException("Product quantity can't be negative");
        }
        storageService.remove(product, quantity);
    }
}
