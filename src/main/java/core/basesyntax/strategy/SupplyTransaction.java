package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;

import static core.basesyntax.db.Storage.getFruits;

public class SupplyTransaction implements Transaction {

    @Override
    public void process(String fruitName, int amount) {
        Fruit fruit = getFruits().stream()
                .filter(f -> f.getName().equals(fruitName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Fruit not in storage: " + fruitName));

        fruit.setQuantity(fruit.getQuantity() + amount);
    }
}
