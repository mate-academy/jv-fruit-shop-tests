package core.basesyntax.service;

import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transactions.FruitTransaction;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;
    private final Storage storage;

    public ShopServiceImpl(OperationStrategy operationStrategy, Storage storage) {
        this.operationStrategy = operationStrategy;
        this.storage = storage;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions can't be null");
        }

        for (FruitTransaction transaction : transactions) {
            if (transaction == null) {
                throw new IllegalArgumentException("Transaction can't be null");
            }

            if (transaction.getFruit() == null) {
                throw new IllegalArgumentException("Fruit can't be null");
            }

            operationStrategy.process(transaction, storage);
        }
    }
}

