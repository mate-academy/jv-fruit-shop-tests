package core.basesyntax.strategy;

import core.basesyntax.exceptions.NullException;
import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.ReturnHandler;
import core.basesyntax.handlers.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
    }

    @Override
    public void process(FruitTransaction.Operation operation, String fruit, Integer quantity) {
        checkFruitTransaction(operation, fruit, quantity);
        operationHandlerMap.get(operation).process(fruit, quantity);
    }

    private void checkFruitTransaction(FruitTransaction.Operation operation,
                                       String fruit, Integer quantity) {
        if (operation == null || fruit == null || quantity == null) {
            throw new NullException("Invalid fruit transaction");
        }
    }
}
