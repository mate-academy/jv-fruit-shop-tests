package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class StoreServiceImpl implements StoreService {
    @Override
    public String getReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit, quantity");
        for (Map.Entry<Fruit, Integer> entry : Storage.fruits.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(entry.getKey().getFruitName())
                    .append(",").append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
