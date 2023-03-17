package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final TransactionStrategy transactionStrategy;
    private final FruitDao fruitDao;

    public FruitServiceImpl(FruitDao fruitDao,
            TransactionStrategy transactionStrategy) {
        this.fruitDao = fruitDao;
        this.transactionStrategy = transactionStrategy;
    }

    @Override
    public void updateAll(List<Transaction> transactions) {
        int fruitQuantity;
        for (Transaction transaction : transactions) {
            fruitQuantity = transactionStrategy
                    .get(transaction.getOperation())
                    .doCalculation(transaction.getQuantity());
            String fruit = transaction.getFruit();
            fruitDao.updateStock(fruit, fruitQuantity);
        }
    }
}
