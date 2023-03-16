package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.Strategy;

import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private final Strategy strategy;

    public FruitShopServiceImpl(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void realizePattern(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            FruitHandler service = strategy.choosePattern(fruitTransaction);
            service.apply(fruitTransaction);
        }
    }
}
