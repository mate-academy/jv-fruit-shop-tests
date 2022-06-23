package core.basesyntax.service;

import core.basesyntax.fruit.FruitTransaction;
import java.util.List;

public interface BalanceCounter {
    void calculateBalance(List<FruitTransaction> fruitsMoving, ActionStrategy mapStrategy);
}
