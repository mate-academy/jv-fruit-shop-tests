package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;

public class PurchaseHandler implements OperationHandler {
    @Override
    public int apply(FruitOperationDto fruitOperationDto) {
        if (Storage.storage.containsKey(fruitOperationDto.getFruit())
                && Storage.storage.get(fruitOperationDto.getFruit())
                >= fruitOperationDto.getQuantity()) {
            return Storage.storage.get(fruitOperationDto.getFruit())
                    - fruitOperationDto.getQuantity();
        }
        throw new RuntimeException("Can't purchase");
    }
}
