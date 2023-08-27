package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {

    @Override
    public void processWithTransaction(FruitTransaction transaction) {
        OperationHandler supply = new SupplyOperation();
        supply.processWithTransaction(transaction);
    }
}
