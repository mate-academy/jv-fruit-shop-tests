package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import java.util.List;
import java.util.Map;

public interface ShopService {
    Map<Fruit, Integer> updateStorageInfo(List<TransactionDto> fruitTransactions);
}
