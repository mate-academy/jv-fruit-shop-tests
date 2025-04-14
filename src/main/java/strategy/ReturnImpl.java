package strategy;

import db.Storage;
import model.Fruit;

public class ReturnImpl implements Operation {
    private static final int ZERO = 0;

    @Override
    public void execute(Fruit fruit) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }

        if (fruit.getName() == null) {
            throw new IllegalArgumentException("Fruit name cannot be null");
        }

        if (fruit.getQuantity() < ZERO) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        int currentQuantity = Storage.storage.getOrDefault(fruit.getName(), ZERO);
        Storage.storage.put(fruit.getName(), currentQuantity + fruit.getQuantity());
    }
}
