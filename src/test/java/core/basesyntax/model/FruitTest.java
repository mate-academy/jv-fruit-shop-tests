package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTest {

    @Test
    void createFruit_Ok() {
        String name = "banana";
        int quantity = 40;
        Fruit fruit = new Fruit(name, quantity);
        assertEquals(name, fruit.getFruitName());
        assertEquals(quantity, fruit.getQuantity());
    }
}
