package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.ActivitiesStrategy;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private ActivitiesStrategy strategy;

    public FruitShopServiceImpl(ActivitiesStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void processData(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            if (fruitTransaction.getOperationCharacter().trim().length() == 1) {
                strategy.get(fruitTransaction.getOperationCharacter().trim())
                        .operation(fruitTransaction);
            } else {
                throw new RuntimeException("invalid input type operation");
            }
        }
    }
}
