package basesyntax.service;

import basesyntax.model.FruitTransaction;
import java.util.List;

public interface ShopService {
    void process(List<FruitTransaction> fruitTransactionList);
}
