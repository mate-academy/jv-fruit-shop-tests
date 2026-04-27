package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.TransactionDao;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy strategy;
    private final TransactionDao transactionDao;
    
    public ShopServiceImpl(OperationStrategy strategy, TransactionDao transactionDao) {
        this.strategy = strategy;
        this.transactionDao = transactionDao;
    }
    
    @Override
    public void process(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            OperationHandler handler = strategy.getOperationHandler(transaction.getOperation());
            handler.handle(transaction);
        }
    }
    
    @Override
    public Map<String, Integer> getData() {
        return transactionDao.getAll();
    }
}
