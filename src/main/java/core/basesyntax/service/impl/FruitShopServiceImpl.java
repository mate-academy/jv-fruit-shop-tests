package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.impl.exception.InvalidDataException;

public class FruitShopServiceImpl implements FruitShopService {
    @Override
    public void process(FruitTransaction fruitTransaction, OperationHandler operation) {
        checkForValidInputParameters(fruitTransaction, operation);
        operation.handle(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity());
    }

    private void checkForValidInputParameters(FruitTransaction fruitTransaction,
                                              OperationHandler operation) {
        if (fruitTransaction == null || operation == null) {
            throw new InvalidDataException("Input parameters cannot contain Null");
        }
    }
}
