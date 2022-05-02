package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface OperationService {
    void calculate(List<FruitTransaction> fruitTransactions);
}
