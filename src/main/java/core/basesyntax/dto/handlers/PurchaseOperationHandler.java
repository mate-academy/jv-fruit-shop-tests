package core.basesyntax.dto.handlers;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.models.FruitRecord;

public class PurchaseOperationHandler implements OperationsHandler {
    @Override
    public void apply(FruitDao daoService, FruitRecord fruitRecord) {
        fruitRecord.setAmount(fruitRecord.getAmount() * (-1));
        daoService.put(fruitRecord);
    }
}
