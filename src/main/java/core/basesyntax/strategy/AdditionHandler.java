package core.basesyntax.strategy;

import core.basesyntax.service.StorageSupplyService;

public class AdditionHandler implements OperationHandler {
    @Override
    public void execute(String fruit, Integer amount, StorageSupplyService supplyService) {
        supplyService.add(fruit, amount);
    }
}
