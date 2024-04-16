package basesyntax.strategy;

import basesyntax.exceptions.TransactionException;
import basesyntax.model.Fruit;
import java.util.Map;

public class FruitSupply implements FruitHandler {
    @Override
    public void transactionHandler(
            Map<String, Fruit> fruitsData,
            String fruitName,
            int fruitQuantity
    ) {
        if (fruitsData == null || fruitName == null) {
            throw new TransactionException(
                    "Impossible to handle transaction - some data is null"
            );
        }
        Fruit fruit = fruitsData.get(fruitName);
        if (fruit == null) {
            Fruit newFruit = new Fruit(fruitName, fruitQuantity);
            fruitsData.put(fruitName, newFruit);
        } else {
            fruit.setQuantity(fruit.getQuantity() + fruitQuantity);
        }
    }
}
