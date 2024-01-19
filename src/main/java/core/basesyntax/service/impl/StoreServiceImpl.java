package core.basesyntax.service.impl;

import core.basesyntax.dao.ArticleDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StoreService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreServiceImpl implements StoreService {
    private static final String COLUMN_NAMES = "fruit,quantity";
    private static final String ROW_SEPARATOR = ",";
    private static final int STARTED_ROW_LENGTH = 0;
    private static final int MIN_QUANTITY_VALUE = 0;
    private ArticleDao fruitTransactionDao;
    private TransactionStrategy transactionStrategy;

    public StoreServiceImpl(ArticleDao fruitTransactionDao,
                            TransactionStrategy transactionStrategy) {
        this.fruitTransactionDao = fruitTransactionDao;
        this.transactionStrategy = transactionStrategy;
    }

    @Override
    public void updateStorage(List<FruitTransaction> transactions) {
        Map<String, Integer> result = new HashMap<>();
        for (FruitTransaction transaction : transactions) {
            transactionStrategy.get(transaction.getOperation()).proceed(result, transaction);
        }
        String fruit;
        int quantity;
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            fruit = entry.getKey();
            quantity = entry.getValue();
            if (quantity >= MIN_QUANTITY_VALUE) {
                fruitTransactionDao.updateStorage(fruit, quantity);
            } else {
                throw new RuntimeException("Incorrect data - the balance of '" + fruit
                        + "' less than 0 \n"
                        + fruit + ": " + quantity);
            }
        }
    }

    @Override
    public List<String> createReport() {
        List<String> report = new ArrayList<>();
        report.add(COLUMN_NAMES);
        StringBuilder newRow = new StringBuilder();
        String article;
        int quantity;
        List<String> articles = fruitTransactionDao.getArticles();
        for (String articleFromStorage : articles) {
            article = articleFromStorage;
            quantity = fruitTransactionDao.getQuantity(article);
            newRow.append(article).append(ROW_SEPARATOR).append(quantity);
            report.add(newRow.toString());
            newRow.setLength(STARTED_ROW_LENGTH);
        }
        return report;
    }
}
