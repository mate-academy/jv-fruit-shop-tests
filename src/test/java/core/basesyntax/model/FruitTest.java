package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTest {

    private static final String FRUIT_NAME = "Apple";
    private static final int INITIAL_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -5;
    private static final int ADD_QUANTITY = 5;
    private static final int REDUCE_QUANTITY = 5;
    private static final int EXCESS_QUANTITY = 10;
    private static final String EXPECTED_FRUIT_TO_STRING = "Fruit{name='Apple', quantity=10}";

    @Test
    void constructor_validParameters_ok() {
        Fruit fruit = new Fruit(FRUIT_NAME, INITIAL_QUANTITY);
        assertEquals(FRUIT_NAME, fruit.getName());
        assertEquals(INITIAL_QUANTITY, fruit.getQuantity());
    }

    @Test
    void constructor_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new Fruit(FRUIT_NAME,
                NEGATIVE_QUANTITY));
    }

    @Test
    void addQuantity_validQuantity_ok() {
        Fruit fruit = new Fruit(FRUIT_NAME, INITIAL_QUANTITY);
        fruit.addQuantity(ADD_QUANTITY);
        assertEquals(INITIAL_QUANTITY + ADD_QUANTITY, fruit.getQuantity());
    }

    @Test
    void addQuantity_negativeQuantity_notOk() {
        Fruit fruit = new Fruit(FRUIT_NAME, INITIAL_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> fruit.addQuantity(NEGATIVE_QUANTITY));
    }

    @Test
    void reduceQuantity_validQuantity_ok() {
        Fruit fruit = new Fruit(FRUIT_NAME, INITIAL_QUANTITY);
        fruit.reduceQuantity(REDUCE_QUANTITY);
        assertEquals(INITIAL_QUANTITY - REDUCE_QUANTITY, fruit.getQuantity());
    }

    @Test
    void reduceQuantity_negativeQuantity_notOk() {
        Fruit fruit = new Fruit(FRUIT_NAME, INITIAL_QUANTITY);
        assertThrows(IllegalArgumentException.class,
                () -> fruit.reduceQuantity(NEGATIVE_QUANTITY));
    }

    @Test
    void reduceQuantity_quantityBelowZero_notOk() {
        Fruit fruit = new Fruit(FRUIT_NAME, EXCESS_QUANTITY);
        assertThrows(IllegalArgumentException.class,
                () -> fruit.reduceQuantity(INITIAL_QUANTITY + 1));
    }

    @Test
    void toString_validFruit_ok() {
        Fruit fruit = new Fruit(FRUIT_NAME, INITIAL_QUANTITY);
        assertEquals(EXPECTED_FRUIT_TO_STRING, fruit.toString());
    }
}
