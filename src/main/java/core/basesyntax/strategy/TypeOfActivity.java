package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public interface TypeOfActivity {
    void realizeType(FruitTransaction fruitTransaction);

    default void checkValidData(Fruit fruit, int currentAmount) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can't be null.");
        }
        if (currentAmount < 1) {
            throw new RuntimeException("Amount of fruit can't be 0 or less.");
        }
    }
}
