package core.basesyntax.strategy.handler;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;

public class SupplyHandler extends OperationHandler {
    public SupplyHandler(TransactionDao transactionDao) {
        super(transactionDao);
    }
    
    @Override
    public void handle(Transaction transaction) {
        transactionDao.add(transaction.getType(), transaction.getQuantity());
    }
}
