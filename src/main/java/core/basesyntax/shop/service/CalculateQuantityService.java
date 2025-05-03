package core.basesyntax.shop.service;

import core.basesyntax.shop.model.FruitTransaction;
import java.util.List;

public interface CalculateQuantityService {
    void calculate(List<FruitTransaction> fruitTransactions);
}
