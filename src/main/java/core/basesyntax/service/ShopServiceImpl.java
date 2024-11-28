package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final ActionStrategy strategy;

    public ShopServiceImpl(ActionStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("Can not add empty list of fruit transactions");
        }
        for (FruitTransaction transaction : transactions) {
            processTransaction(transaction);
        }
    }

    private void processTransaction(FruitTransaction transaction) {
        strategy.get(transaction.getOperation())
                .count(transaction.getFruit(), transaction.getQuantity());
    }
}
