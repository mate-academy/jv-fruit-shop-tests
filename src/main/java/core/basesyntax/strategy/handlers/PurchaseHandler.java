package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.util.TransactionValidator;

public class PurchaseHandler implements OperationHandler {
    private final TransactionValidator transactionValidator = new TransactionValidator();

    @Override
    public void apply(FruitTransaction transaction) {
        transactionValidator.validate(transaction);
        int currentQuantity = Storage.getFruitQuantity(transaction.getFruit());
        if (currentQuantity < transaction.getQuantity()) {
            throw new IllegalArgumentException("Not enough fruit in stock to "
                    + "complete the purchase");
        }
        Storage.addFruit(transaction.getFruit(), -transaction.getQuantity());
    }
}

