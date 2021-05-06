package core.basesyntax.service.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.fruit.dto.FruitDto;
import java.util.Map;

public class UpdateFruitHandlerImpl implements FruitHandler {
    @Override
    public void handleGoods(FruitDto fruitData) {
        int amount = fruitData.getAmount();
        Map<String, Integer> fruitStorage = Storage.getFruitStorage();
        String fruitType = fruitData.getFruitType();
        if (fruitStorage.containsKey(fruitType)) {
            fruitStorage.put(fruitType,
                    fruitStorage.get(fruitType) + amount);
        } else {
            fruitStorage.put(fruitType, amount);
        }
    }
}
