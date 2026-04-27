package core.basesyntax.strategy.handler;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;

public class PurchaseHandler extends OperationHandler {
    public PurchaseHandler(TransactionDao transactionDao) {
        super(transactionDao);
    }
    
    @Override
    public void handle(Transaction transaction) {
        transactionDao.subtract(transaction.getType(), transaction.getQuantity());
    }
}
