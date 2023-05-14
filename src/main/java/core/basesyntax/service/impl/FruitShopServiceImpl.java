package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private TransactionStrategy transactionStrategy;

    public FruitShopServiceImpl(TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
    }

    @Override
    public void processTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transactionStrategy.handleOperation(transaction);
        }
    }
}
