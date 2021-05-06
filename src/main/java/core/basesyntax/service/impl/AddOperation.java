package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;

public class AddOperation implements FruitOperationHandler {

    @Override
    public int apply(FruitRecordDto fruitRecordDto) {
        Fruit fruit = new Fruit(fruitRecordDto.getFruitName());
        int quantity = fruitRecordDto.getQuantity();
        if (quantity < 0) {
            throw new RuntimeException("Negative value of the number of fruits");
        }
        if (!Storage.fruits.containsKey(fruit)) {
            Storage.fruits.put(fruit, quantity);
        } else {
            Storage.fruits.put(fruit, Storage.fruits.get(fruit) + quantity);
        }
        return quantity;
    }
}
