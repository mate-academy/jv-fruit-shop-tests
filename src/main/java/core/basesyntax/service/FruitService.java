package core.basesyntax.service;

import java.util.List;
import core.basesyntax.model.FruitTransaction;

public interface FruitService {
    void calculateTotalQuantity(List<FruitTransaction> transactions);
}
