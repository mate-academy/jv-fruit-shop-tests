package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitShopService {
    void addDataToStorage(List<FruitTransaction> dataList);
}
