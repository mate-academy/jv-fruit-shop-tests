package core.basesyntax.service.read;

import core.basesyntax.model.FruitTransaction;

public interface OperationMapper {
    FruitTransaction.Operation mapToOperation(String operation);
}
