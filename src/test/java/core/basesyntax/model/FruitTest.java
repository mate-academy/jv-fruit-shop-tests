package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTest {

    @Test
    public void testConstructorAndGetters_Ok() {
        String fruitName = "apple";
        int quantity = 10;
        Fruit fruit = new Fruit(fruitName, quantity);
        Assertions.assertEquals(fruitName, fruit.getFruitName());
        Assertions.assertEquals(quantity, fruit.getQuantity());
    }
}
