package core.basesyntax.service.operation;

import core.basesyntax.model.FruitRecordDto;

public interface Operation {
    void apply(FruitRecordDto fruitRecordDto);
}
