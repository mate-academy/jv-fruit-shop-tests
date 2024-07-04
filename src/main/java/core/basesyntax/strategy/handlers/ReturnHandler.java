package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.util.TransactionValidator;

public class ReturnHandler implements OperationHandler {

    private final TransactionValidator transactionValidator = new TransactionValidator();

    @Override
    public void apply(FruitTransaction transaction) {
        transactionValidator.validate(transaction);
        Storage.addFruit(transaction.getFruit(), transaction.getQuantity());
    }
}

