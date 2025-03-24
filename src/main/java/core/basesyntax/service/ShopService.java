package core.basesyntax.service;

import java.util.List;
import java.util.Map;

import core.basesyntax.model.FruitTransaction;

public interface ShopService {
    void process(List<FruitTransaction> transactions);
    Map<String, Integer> getStorage();
}
