package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ActivityStrategy;
import core.basesyntax.service.ShopService;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private ActivityStrategy strategy;

    public ShopServiceImpl(ActivityStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean processTransactions(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Input value can't be null");
        }
        transactions.forEach(t -> strategy.getHandler(this.checkOnNullTransaction(t)
                .getOperation()).handle(t));
        return true;
    }

    private FruitTransaction checkOnNullTransaction(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null || fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null
                || fruitTransaction.getAmount() == null) {
            throw new RuntimeException("Input list of transactions with null value");
        }
        return fruitTransaction;
    }
}
