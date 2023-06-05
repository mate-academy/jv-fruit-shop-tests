package core.basesyntax.service.operation;

import core.basesyntax.models.FruitTransaction;
import java.util.Map;

public class OperationHandlerMap {
    public static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
            FruitTransaction.Operation.RETURN, new ReturnHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyHandler());
}
