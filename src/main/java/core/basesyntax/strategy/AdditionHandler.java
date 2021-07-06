package core.basesyntax.strategy;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class AdditionHandler implements OperationHandler {
    private Map<Fruit, Integer> storage;

    public AdditionHandler(Map<Fruit, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public int apply(FruitDto fruitDto) {
        Fruit fruit = new Fruit(fruitDto.getName());
        if (!storage.containsKey(fruit)) {
            throw new RuntimeException(fruit.getName() + " isn't exist");
        }
        int count = storage.get(fruit);
        int result = fruitDto.getQuantity() + count;
        storage.put(fruit, result);
        return result;
    }
}
