package service;

import dao.TransactionsDao;
import java.util.List;
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
    public void processCsv() {
        transactions.forEach(transactionsDao::processTransaction);
    }
}
