package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface OperationHandler {
    void handleOperation(FruitTransaction transaction,
                         FruitStorage fruitStorage, List<String> errorMessages);
}
