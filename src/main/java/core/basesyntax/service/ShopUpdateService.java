package core.basesyntax.service;

import core.basesyntax.transactor.FruitTransaction;
import java.util.List;

public interface ShopUpdateService {
    void update(List<FruitTransaction> data);
}
