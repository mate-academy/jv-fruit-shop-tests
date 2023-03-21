package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.CalculateStrategy;
import core.basesyntax.strategy.impl.CalculateStrategyImpl;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final CalculateStrategy calculateStrategy = new CalculateStrategyImpl();

    @Override
    public void calculateTotalQuantity(List<FruitTransaction> transactions) {
        for (FruitTransaction fruitTransaction : transactions) {
            calculateStrategy.getHandler(fruitTransaction)
                    .apply(fruitTransaction);
        }
    }
}
