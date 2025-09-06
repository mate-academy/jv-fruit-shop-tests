package core.basesyntax.operationstrategy;

import core.basesyntax.fruittransactionhandler.OperationHandler;
import core.basesyntax.model.TypeOfTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategyService {
    private final Map<TypeOfTransaction, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<TypeOfTransaction, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getHandler(TypeOfTransaction typeOfTransaction) {
        if (typeOfTransaction == null) {
            throw new RuntimeException("Type of transaction is null.");
        }
        OperationHandler result = operationHandlerMap.get(typeOfTransaction);
        if (result == null) {
            throw new RuntimeException("Handler for this operation doesn't exist.");
        }
        return result;
    }
}
