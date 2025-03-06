package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public interface Operation {
    Map<String, Integer> update(List<FruitTransaction> data);
}
