package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.TransactionDto;
import java.util.List;
import java.util.Map;

public interface FruitService {
    Map<Fruit, Integer> saveDataToDb(List<TransactionDto> data);
}
