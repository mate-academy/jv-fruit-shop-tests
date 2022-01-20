package core.basesyntax.strategy;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class BalanceHandler implements OperationHandler {
    private final Map<Fruit, Integer> storage;

    public BalanceHandler(Map<Fruit, Integer> storage) {
        if (storage == null) {
            throw new RuntimeException("arg Map<Fruit, Integer> is null",
                    new NullPointerException());
        }
        this.storage = storage;
    }

    @Override
    public int apply(FruitDto fruitDto) {
        storage.put(new Fruit(fruitDto.getName()), fruitDto.getQuantity());
        return fruitDto.getQuantity();
    }
}
