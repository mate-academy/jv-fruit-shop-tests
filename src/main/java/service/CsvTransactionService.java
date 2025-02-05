package service;

import dao.TransactionsDao;
import java.util.List;
import java.util.Map;

import model.FruitTransaction;

public class CsvTransactionService implements Processor {
    private final TransactionsDao transactionsDao;
    private final List<FruitTransaction> transactions;

    public CsvTransactionService(
            TransactionsDao transactionsDao, List<FruitTransaction> transactions) {
        this.transactions = transactions;
        this.transactionsDao = transactionsDao;
    }

    @Override
    public Map<String, Integer> processCsv() {
        transactions.forEach(transactionsDao::processTransaction);
        return transactionsDao.getAll();
    }
}
