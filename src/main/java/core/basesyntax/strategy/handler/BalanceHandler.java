package core.basesyntax.strategy.handler;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;

public class BalanceHandler extends OperationHandler {
    public BalanceHandler(TransactionDao transactionDao) {
        super(transactionDao);
    }
    
    @Override
    public void handle(Transaction transaction) {
        transactionDao.set(transaction.getType(), transaction.getQuantity());
    }
}
