package core.basesyntax.strategy;

import java.util.Map;

public class PurchaseOperation implements FruitOperationStrategy {

    @Override
    public void execute(Map<String, Integer> fruitQuantity, String fruit, int quantity) {
        if (fruitQuantity.get(fruit) - quantity < 0) {
            throw new RuntimeException("You can't sell more then you have!");
        }
        fruitQuantity.put(fruit, fruitQuantity.get(fruit) - quantity);
    }
}

