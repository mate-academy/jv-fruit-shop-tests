package fruitshop.suppliers;

import fruitshop.model.Operation;
import fruitshop.operation.OperationHandler;
import fruitshop.operation.OperationStrategy;
import fruitshop.operation.impl.BalanceOperationHandlerImpl;
import fruitshop.operation.impl.OperationStrategyImpl;
import fruitshop.operation.impl.PurchaseOperationHandlerImpl;
import fruitshop.operation.impl.ReturnOperationHandlerImpl;
import fruitshop.operation.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategySupplier {
    private final OperationStrategy operationStrategy;

    public OperationStrategySupplier() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        putOperations(operationHandlers);
    }

    private void putOperations(Map<Operation, OperationHandler> operationHandlers) {
        operationHandlers.put(Operation.BALANCE, new BalanceOperationHandlerImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandlerImpl());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        operationHandlers.put(Operation.RETURN, new ReturnOperationHandlerImpl());
    }

    public OperationStrategy get() {
        return operationStrategy;
    }
}
