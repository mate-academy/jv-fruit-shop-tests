package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
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
        if (fruitTransactions.isEmpty() || fruitTransactions == null) {
            throw new RuntimeException("List of transaction can`t be empty or null "
                    + fruitTransactions);
        }
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            FruitHandler service = strategy.choosePattern(fruitTransaction);
            service.apply(fruitTransaction);
        }
    }
}
