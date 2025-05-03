package basesyntax.service;

import basesyntax.model.FruitRecordDto;
import basesyntax.service.operation.OperationHandler;

public interface OperationStrategy {
    OperationHandler get(FruitRecordDto.Type operation);
}
