package service;

import dao.TransactionsDao;
import java.util.List;
import db.Storage;
import model.FruitTransaction;
import strategy.OperationStrategy;
import strategy.TransactionHandler;

public class CsvTransactionService implements Processor {

    private final TransactionsDao transactionsDao;
    private final OperationStrategy operationStrategy;

    public CsvTransactionService(
        TransactionsDao transactionsDao, OperationStrategy operationStrategy) {
        this.transactionsDao = transactionsDao;
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processCsv(List<FruitTransaction> transactions) {
        transactions.forEach(transaction -> {
            int currentQuantity = transactionsDao.getAll().getOrDefault(transaction.getFruit(), 0);
            TransactionHandler handler = operationStrategy.getStrategy(transaction.getOperation());
            int updatedQuantity = handler.apply(currentQuantity, transaction);
            Storage.fruitsStore.put(transaction.getFruit(), updatedQuantity);
        });
    }
}
