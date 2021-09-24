package core.basesyntax.dto.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.models.FruitRecord;

public interface OperationsHandler {
    void apply(FruitDao daoService, FruitRecord fruitRecord);
}
