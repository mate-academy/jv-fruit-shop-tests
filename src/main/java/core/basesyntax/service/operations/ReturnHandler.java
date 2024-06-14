package core.basesyntax.service.operations;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CantWorkWithThisFileException;

public class ReturnHandler implements OperationHandler {
    @Override
    public int getOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new CantWorkWithThisFileException("fruitTransaction is null");
        }
        return fruitTransaction.getQuantity();
    }
}
