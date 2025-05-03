package core.basesyntax.data.servise.ipl;

import core.basesyntax.data.exeption.OperationException;
import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.servise.StrategyOperationService;
import core.basesyntax.data.strategy.OperationHandler;
import java.util.Map;

public class StrategyOperationServiceImpl implements StrategyOperationService {
    private Map<OperationType, OperationHandler> operationHandlerMap;

    public StrategyOperationServiceImpl(Map<OperationType, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler get(OperationType operationType) {
        OperationHandler handler = operationHandlerMap.get(operationType);
        if (handler != null) {
            return handler;
        }
        throw new OperationException("Operation type is not correct: " + operationType);
    }
}
