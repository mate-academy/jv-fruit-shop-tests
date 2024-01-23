package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface StrategyImplementation {
    void strategy(List<FruitTransaction> fruitTransactions);
}
