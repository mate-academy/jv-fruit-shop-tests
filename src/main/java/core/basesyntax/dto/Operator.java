package core.basesyntax.dto;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dto.handlers.OperationsHandler;
import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Operator {
    private Map<Character, OperationsHandler> typesOfOperations = new HashMap();

    public Map<Character, OperationsHandler> getTypesOfOperations() {
        return typesOfOperations;
    }

    public Set<Fruit> doAllOperation(List<FruitRecord> recordList, FruitDao storage) {
        if (storage == null) {
            throw new RuntimeException("null instead of storage");
        }
        if (recordList == null) {
            throw new RuntimeException("null instead of recordList");
        }
        if (recordList.contains(null)) {
            throw new RuntimeException("null-reference in recordList");
        }
        recordList
                .forEach(e -> typesOfOperations.get(e.getTypeOfOperation()).apply(storage, e));
        return storage.get();
    }
}
