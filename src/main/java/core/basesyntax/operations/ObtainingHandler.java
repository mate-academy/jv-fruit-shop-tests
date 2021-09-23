package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;

public class ObtainingHandler implements OperationHandler {

    @Override
    public int apply(FruitOperationDto fruitOperationDto) {
        if ((Storage.storage.containsKey(fruitOperationDto.getFruit()))) {
            return Storage.storage.get(fruitOperationDto.getFruit())
                    + fruitOperationDto.getQuantity();
        }
        OperationHandler operationHandler = new BalanceHandler();
        return operationHandler.apply(fruitOperationDto);
    }
}
