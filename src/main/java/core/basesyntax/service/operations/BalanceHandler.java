package core.basesyntax.service.operations;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CantWorkWithThisFileException;

public class BalanceHandler implements OperationHandler {
    @Override
    public int getOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new CantWorkWithThisFileException("fruitTransaction is null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new CantWorkWithThisFileException("How quantity can be < 0?");
        }
        return fruitTransaction.getQuantity();
    }
}
