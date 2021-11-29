package core.basesyntax.dto.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.models.FruitRecord;

public class BalanceOperationHandler implements OperationsHandler {
    @Override
    public void apply(FruitDao daoService, FruitRecord fruitRecord) {
        daoService.save(fruitRecord);
    }
}
