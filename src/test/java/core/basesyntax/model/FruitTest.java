package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTest {

    @Test
    void constructor_shouldInitializeCorrectly() {
        Fruit fruit = new Fruit("Apple", 10);
        assertEquals("Apple", fruit.getName());
        assertEquals(10, fruit.getQuantity());
    }

    @Test
    void constructor_shouldThrowExceptionForNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Fruit("Apple", -5));
    }

    @Test
    void addQuantity_shouldIncreaseQuantityCorrectly() {
        Fruit fruit = new Fruit("Apple", 10);
        fruit.addQuantity(5);
        assertEquals(15, fruit.getQuantity());
    }

    @Test
    void addQuantity_shouldThrowExceptionForNegativeQuantity() {
        Fruit fruit = new Fruit("Apple", 10);
        assertThrows(IllegalArgumentException.class, () -> fruit.addQuantity(-3));
    }

    @Test
    void reduceQuantity_shouldDecreaseQuantityCorrectly() {
        Fruit fruit = new Fruit("Apple", 10);
        fruit.reduceQuantity(5);
        assertEquals(5, fruit.getQuantity());
    }

    @Test
    void reduceQuantity_shouldThrowExceptionForNegativeQuantity() {
        Fruit fruit = new Fruit("Apple", 10);
        assertThrows(IllegalArgumentException.class, () -> fruit.reduceQuantity(-3));
    }

    @Test
    void reduceQuantity_shouldThrowExceptionForQuantityBelowZero() {
        Fruit fruit = new Fruit("Apple", 5);
        assertThrows(IllegalArgumentException.class, () -> fruit.reduceQuantity(10));
    }

    @Test
    void toString_shouldReturnProperStringRepresentation() {
        Fruit fruit = new Fruit("Apple", 10);
        assertEquals("Fruit{name='Apple', quantity=10}", fruit.toString());
    }
}
