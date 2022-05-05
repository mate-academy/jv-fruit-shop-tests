package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransferDto;
import core.basesyntax.service.inerfaces.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<String, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<String, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public void handle(FruitTransferDto dto) {
        operationHandlerMap.get(dto.getOperationType())
                .doOperation(dto.getFruit(), dto.getQuantity());
    }
}
