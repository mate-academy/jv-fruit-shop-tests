package core.basesyntax.service;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.type.service.OperationHandler;

public interface OperationStrategy {
    OperationHandler getHandler(FruitRecordDto.Activities type);
}
