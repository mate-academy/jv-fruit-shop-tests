package basesyntax.strategy;

import basesyntax.exceptions.TransactionException;
import basesyntax.model.Fruit;
import java.util.Map;

public class FruitPurchase implements FruitHandler {
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
            throw new TransactionException("No fruits in storage to handle purchase");
        } else if (fruit.getQuantity() < fruitQuantity) {
            throw new TransactionException(
                    "Impossible to handle purchase, because of the lack of fruits"
            );
        }
        fruit.setQuantity(fruit.getQuantity() - fruitQuantity);
    }
}
