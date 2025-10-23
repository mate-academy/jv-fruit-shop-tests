package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitDto;
import core.basesyntax.service.FruitOperation;
import java.util.List;
import java.util.stream.Collectors;

public class FruitReduction implements FruitOperation {
    @Override
    public void apply(FruitDto fruitDto) {
        if (fruitDto.getQuantity() <= 0) {
            throw new RuntimeException("Quantity should be positive");
        }
        List<Fruit> fruitsToRemove = Storage.fruits.stream()
                .filter(fruit -> fruit.getName().equals(fruitDto.getFruit()))
                .limit(fruitDto.getQuantity())
                .collect(Collectors.toList());
        if (fruitsToRemove.size() < fruitDto.getQuantity()) {
            throw new RuntimeException("Not enough fruits to reduce");
        }
        Storage.fruits.removeAll(fruitsToRemove);
    }
}
