package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ShopService {
    boolean processTransactions(List<FruitTransaction> transactions);

}
