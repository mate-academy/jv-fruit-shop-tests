package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitShopService {
    void processData(List<FruitTransaction> transactionList);
}
