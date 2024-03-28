package core.basesyntax.service.strategy.impl;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.strategy.Operation;
import core.basesyntax.service.strategy.OperationStrategy;
import exception.CustomException;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> strategyMap;

    public OperationStrategyImpl(Map<Operation, OperationHandler> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    public OperationHandler get(FruitTransactionDto dto) {
        if (dto.operation() == null) {
            throw new CustomException("Operation cannot be null");
        }
        Operation operation = Operation.validateOperation(dto.operation().getCode());
        return strategyMap.get(operation);
    }
}
