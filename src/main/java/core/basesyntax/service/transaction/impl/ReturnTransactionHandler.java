package core.basesyntax.service.transaction.impl;

import core.basesyntax.service.transaction.TransactionHandler;

public class ReturnTransactionHandler implements TransactionHandler {
    @Override
    public int perform(int balance, int quantity) {
        return balance + quantity;
    }
}
