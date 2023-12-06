package core.basesyntax.service;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ServiceFruitShop {
    FruitTransactionDb processTransaction(List<FruitTransaction> fruitTransaction);
}
