package core.basesyntax.service.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.StorageServiceImpl;

public class ReturnHandler implements OperationHandler {
    private StorageService storageService;

    public ReturnHandler() {
        storageService = new StorageServiceImpl();
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("Can't handle null data.");
        }
        storageService.add(new Fruit(fruitTransaction.getFruit()), fruitTransaction.getQuantity());
    }
}
