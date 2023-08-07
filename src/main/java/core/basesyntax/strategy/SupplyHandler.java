package core.basesyntax.strategy;

import core.basesyntax.service.StorageService;

public class SupplyHandler implements FruitShopOperationsHandler {
    private StorageService storageService;

    public SupplyHandler(StorageService storageService) {
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
        storageService.add(product, quantity);
    }
}
