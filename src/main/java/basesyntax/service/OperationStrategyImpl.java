package basesyntax.service;

import basesyntax.model.FruitRecordDto;
import basesyntax.service.operation.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitRecordDto.Type, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitRecordDto.Type, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler get(FruitRecordDto.Type operation) {
        if (operationHandlerMap.get(operation) == null) {
            throw new RuntimeException("Invalid operation");
        }
        return operationHandlerMap.get(operation);
    }
}
