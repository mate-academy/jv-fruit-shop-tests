package core.basesyntax.strategy.operationmap;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transactionhandlers.TransactionHandler;
import java.util.Map;

public interface OperationMap {
    Map<FruitTransaction.Operation, TransactionHandler> getOperationMap();

    void addOperationToMap(FruitTransaction.Operation operation,
                           TransactionHandler handler);
}
