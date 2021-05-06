package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.Fruit;
import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.OperationHandler;

public class AddOperationHandler implements OperationHandler {
    @Override
    public int apply(FruitRecordDto fruitRecordDto) {
        Fruit fruit = new Fruit(fruitRecordDto.getFruitName());
        Integer currentQuantity = Storage.fruits.get(fruit);
        int newQuantity = currentQuantity + fruitRecordDto.getQuantity();
        Storage.fruits.put(fruit, newQuantity);
        return newQuantity;
    }
}
