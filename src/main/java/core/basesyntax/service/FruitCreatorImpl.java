package core.basesyntax.service;

import core.basesyntax.model.Fruit;

public class FruitCreatorImpl implements FruitCreator {
    @Override
    public Fruit createFruit(String fruitName, String amount) {
        Fruit fruit = new Fruit();
        fruit.setName(fruitName);
        fruit.setAmount(Integer.parseInt(amount));
        return fruit;
    }
}
