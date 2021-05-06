package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.dto.FruitRecordDto;

public interface OperationHandler {
    int apply(FruitRecordDto fruitRecordDto);
}
