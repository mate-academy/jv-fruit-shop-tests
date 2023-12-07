package core.basesyntax.service.impl;

import core.basesyntax.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataHandler;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;

public class DataHandlerImpl implements DataHandler {
    private final TransactionStrategy strategy;

    public DataHandlerImpl(TransactionStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void processTransaction(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new FruitShopException("transactions cannot be null");
        }
        for (FruitTransaction transaction : transactions) {
            strategy.get(transaction.getOperation()).handleTransaction(transaction);
        }
    }
}
