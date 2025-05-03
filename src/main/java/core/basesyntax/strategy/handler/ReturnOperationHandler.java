package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.CantReturnFruitException;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public Integer processData(String fruitName, Integer quantity) {
        if (!Storage.getStorage().containsKey(fruitName)) {
            throw new CantReturnFruitException("There isn't " + fruitName
                    + " in Storage, can't return");
        }
        return Storage.getStorage().compute(fruitName, (key, oldValue)
                -> oldValue == null ? quantity : oldValue + quantity);
    }
}
