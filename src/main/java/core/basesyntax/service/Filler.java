package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface Filler {
    void fillStorage(List<FruitTransaction> fruitTransactions);
}
