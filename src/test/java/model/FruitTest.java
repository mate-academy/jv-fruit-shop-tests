package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTest {
    private Fruit fruit;

    @BeforeEach
    void setUp() {
        fruit = new Fruit("banana", 125);
    }

    @Test
    void getName_ok() {
        Fruit expected = new Fruit("banana", 125);
        assertEquals(expected.getName(), fruit.getName(),
                "Actual and expected fruit name must be equals");
    }

    @Test
    void getQuantity() {
        Fruit expected = new Fruit("banana", 125);
        assertEquals(expected.getQuantity(), fruit.getQuantity(),
                "Actual and expected fruit q-ty must be equals");
    }
}