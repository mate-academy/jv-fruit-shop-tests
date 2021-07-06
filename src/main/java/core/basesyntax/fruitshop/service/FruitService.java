package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.service.shopoperation.OperationHandler;
import core.basesyntax.fruitshop.service.shopoperation.OperationType;

public interface FruitService {
    OperationHandler getOperation(OperationType operationType);
}
