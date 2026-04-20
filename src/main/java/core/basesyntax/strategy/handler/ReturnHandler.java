package core.basesyntax.strategy.handler;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;

public class ReturnHandler extends OperationHandler {
    public ReturnHandler(TransactionDao transactionDao) {
        super(transactionDao);
    }
    
    @Override
    public void handle(Transaction transaction) {
        transactionDao.add(transaction.getType(), transaction.getQuantity());
    }
}
