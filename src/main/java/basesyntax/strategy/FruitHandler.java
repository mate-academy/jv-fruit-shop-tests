package basesyntax.strategy;

import basesyntax.model.Fruit;
import java.util.Map;

public interface FruitHandler {
    void transactionHandler(
            Map<String, Fruit> fruitsData,
            String fruitName,
            int fruitQuantity
    );
}
