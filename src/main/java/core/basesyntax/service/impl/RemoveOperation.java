package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.DataValidation;
import core.basesyntax.service.Operation;
import java.util.Optional;

public class RemoveOperation implements Operation {
    private DataValidation removeValidation = new DataValidationImpl();

    @Override
    public int apply(FruitRecordDto fruitRecordDto) {
        Fruit fruit = new Fruit(fruitRecordDto.getFruitType());
        checkFruitType(fruit.getType());
        Optional<Integer> currentQuantity = Optional.ofNullable(Storage.fruits.get(fruit));
        int desiredQuantity = fruitRecordDto.getQuantity();
        checkQuantity(desiredQuantity);
        int newQuantity = 0;
        if (currentQuantity.isPresent()) {
            if (removeValidation.removeCheck(currentQuantity.get(), desiredQuantity)) {
                newQuantity = currentQuantity.get() - desiredQuantity;
            }
        } else {
            int current = 0;
            if (removeValidation.removeCheck(current, desiredQuantity)) {
                newQuantity = current - desiredQuantity;
            }
        }

        Storage.fruits.put(fruit, newQuantity);
        return newQuantity;
    }
}
