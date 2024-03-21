package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class NoneExistedOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction input) {
        Storage.dataBase.put(input.fruit(), input.quantity());
    }

    @Override
    public boolean isAplicable(FruitTransaction input) {
        return null == input.operation();
    }
}
