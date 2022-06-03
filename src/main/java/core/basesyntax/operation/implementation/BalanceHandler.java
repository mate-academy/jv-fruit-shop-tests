package core.basesyntax.operation.implementation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.FruitService;

public class BalanceHandler implements OperationHandler {
    private final FruitService fruitService;

    public BalanceHandler(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        fruitService.add(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
