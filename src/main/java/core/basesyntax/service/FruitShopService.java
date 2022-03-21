package core.basesyntax.service;

import core.basesyntax.model.FruitRecord;
import java.util.List;

public interface FruitShopService {
    void transfer(List<FruitRecord> transferFruitList);
}
