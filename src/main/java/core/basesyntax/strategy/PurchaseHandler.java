package core.basesyntax.strategy;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class PurchaseHandler implements OperationHandler {
    private final Map<Fruit, Integer> storage;

    public PurchaseHandler(Map<Fruit, Integer> storage) {
        if (storage == null) {
            throw new RuntimeException("arg Map<Fruit, Integer> is null",
                    new NullPointerException());
        }
        this.storage = storage;
    }

    @Override
    public int apply(FruitDto fruitDto) {
        Fruit fruit = new Fruit(fruitDto.getName());
        if (!storage.containsKey(fruit)) {
            throw new RuntimeException(fruit.getName() + " isn't exist");
        }
        int count = storage.get(fruit);
        if (count - fruitDto.getQuantity() < 0) {
            throw new RuntimeException(fruit.getName() + " is not enough");
        }
        int result = count - fruitDto.getQuantity();
        storage.put(fruit, result);
        return result;
    }
}
