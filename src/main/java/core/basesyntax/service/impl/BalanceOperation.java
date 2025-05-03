package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import java.util.Optional;

public class BalanceOperation implements FruitOperationHandler {
    @Override
    public int apply(FruitRecordDto fruitRecordDto) {
        Fruit fruit = new Fruit(fruitRecordDto.getFruitName());
        Integer currentQuantity = Optional.ofNullable(fruitRecordDto.getQuantity()).orElse(0);
        Storage.fruits.put(fruit, currentQuantity);
        return Storage.fruits.get(fruit);
    }

    @Override
    public String getOperationType() {
        return "b";
    }
}
