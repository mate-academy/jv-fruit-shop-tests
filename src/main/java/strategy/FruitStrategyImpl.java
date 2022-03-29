package strategy;

import java.util.Map;
import model.FruitTransaction;
import operation.OperationHandler;

public class FruitStrategyImpl implements FruitStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap;

    public FruitStrategyImpl(
            Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap) {
        this.operationOperationHandlerMap = operationOperationHandlerMap;
    }

    @Override
    public OperationHandler proceed(FruitTransaction fruitTransaction) {
        return operationOperationHandlerMap.get(
                fruitTransaction.getOperation()).handle(fruitTransaction);
    }
}
