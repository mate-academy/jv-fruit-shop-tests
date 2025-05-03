package core.basesyntax.service;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.OperationHandler;

public class FruitShopServiceImpl implements FruitShopService {
    @Override
    public void transfer(OperationStrategy operationStrategy) {
        for (FruitRecordDto temp : Storage.records) {
            OperationHandler resultStrategy = operationStrategy.getHandler(temp.getType());
            Integer result = resultStrategy
                    .getType(temp.getAmount(), Storage.fruitQuantity.get(temp.getFruit()));
            Storage.fruitQuantity.put(temp.getFruit(), result);
        }
    }
}
