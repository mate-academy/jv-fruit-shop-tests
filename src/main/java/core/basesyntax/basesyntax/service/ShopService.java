package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ShopService {
    StorageImpl process(List<FruitTransaction> transactions);
}
