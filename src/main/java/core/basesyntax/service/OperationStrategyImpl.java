package core.basesyntax.service;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitRecordDto.Activities, OperationHandler> mapTypeHandler;

    public OperationStrategyImpl(Map<FruitRecordDto.Activities, OperationHandler> mapTypeHandler) {
        this.mapTypeHandler = mapTypeHandler;
    }

    @Override
    public OperationHandler getHandler(FruitRecordDto.Activities type) {
        return mapTypeHandler.get(type);
    }
}
