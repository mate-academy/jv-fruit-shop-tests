package core.basesyntax.dto.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.models.FruitRecord;

public class ReturnOperationHandler implements OperationsHandler {
    @Override
    public void apply(FruitDao daoService, FruitRecord fruitRecord) {
        daoService.put(fruitRecord);
    }
}
