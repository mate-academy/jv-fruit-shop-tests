package core.basesyntax.strategy;

import core.basesyntax.dbtest.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class BalanceHandler implements OperationHandler {
    private Map<Fruit, Integer> storage;

    public BalanceHandler(Map<Fruit, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public int apply(FruitDto fruitDto) {
        storage.put(new Fruit(fruitDto.getName()), fruitDto.getQuantity());
        return fruitDto.getQuantity();
    }
}
