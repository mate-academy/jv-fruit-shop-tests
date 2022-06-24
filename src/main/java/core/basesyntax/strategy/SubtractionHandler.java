package core.basesyntax.strategy;

import core.basesyntax.service.StorageSupplyService;

public class SubtractionHandler implements OperationHandler {
    @Override
    public void execute(String fruit, Integer amount, StorageSupplyService supplyService) {
        supplyService.subtract(fruit, amount);
    }
}
