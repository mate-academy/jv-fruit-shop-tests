package core.basesyntax.strategy.impl;

import core.basesyntax.services.ParametrsValidatorService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private ParametrsValidatorService parametrsValidator;
    private final Map<String, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<String, OperationHandler> operationHandlerMap,
                                 ParametrsValidatorService parametrsValidator) {
        this.parametrsValidator = parametrsValidator;
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperation(String operationType) {
        parametrsValidator.isOperationValid(operationType);
        parametrsValidator.isParametrsNotNull(operationType);
        return operationHandlerMap.get(operationType);
    }
}
