package core.basesyntax.service.oparation;

import core.basesyntax.model.FruitRecordDto;

public interface OperationHandler {
    void apply(FruitRecordDto fruitRecordDto);
}
