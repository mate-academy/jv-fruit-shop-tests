package strategy;

import model.FruitTransaction;

import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, TransactionHandler> operationHandlers
            = new HashMap<>();

    public OperationStrategyImpl() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
    }

    @Override
    public TransactionHandler getStrategy(FruitTransaction.Operation operationType) {
        return operationHandlers.get(operationType);
    }
}
