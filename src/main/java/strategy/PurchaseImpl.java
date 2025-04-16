package strategy;

import db.Storage;
import model.Fruit;

public class PurchaseImpl implements Operation {
    private static final int ZERO = 0;
    private final Storage storage;

    public PurchaseImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(Fruit fruit) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }

        if (fruit.getName() == null) {
            throw new IllegalArgumentException("Fruit name cannot be null");
        }

        if (fruit.getQuantity() < ZERO) {
            throw new IllegalArgumentException("Purchase quantity cannot be negative");
        } else if (fruit.getQuantity() == ZERO) {
            throw new IllegalArgumentException("You can buy zero of " + fruit.getName()
                    + " fruits");
        }

        int currentQuantity = Storage.storage.getOrDefault(fruit.getName(), ZERO);
        int newQuantity = currentQuantity - fruit.getQuantity();
        if (newQuantity < ZERO) {
            throw new IllegalArgumentException("Cannot purchase more fruits than available "
                    + "for fruit: " + fruit.getName() + ". Available: " + fruit.getName());
        }

        storage.put(fruit.getName(), newQuantity);
    }
}
