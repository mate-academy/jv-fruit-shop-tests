package core.basesyntax.handler.impl;

import core.basesyntax.db.*;
import core.basesyntax.handler.*;
import core.basesyntax.model.*;

public class ReturnOperationHandler implements ShopOperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Integer oldQuantityValue = Storage.fruitStorage.get(fruitTransaction.getFruit());
        oldQuantityValue = oldQuantityValue == null ? 0 : oldQuantityValue;
        Storage.fruitStorage.put(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity() + oldQuantityValue);
    }
}
