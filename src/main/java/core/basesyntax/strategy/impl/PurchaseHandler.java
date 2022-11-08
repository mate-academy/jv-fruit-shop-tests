package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.GeneralOperation;
import core.basesyntax.strategy.TransactionHandler;

public class PurchaseHandler implements GeneralOperation {
    private final TransactionHandler transactionHandler;

    public PurchaseHandler() {
        transactionHandler = new TransactionHandlerImpl();
    }

    @Override
    public void generalOperation(FruitTransaction transaction) {
        try {
            transactionHandler.takeFromBalance(transaction.getFruit(), transaction.getQuantity());
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't take from balance");
        }
    }
}
