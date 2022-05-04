package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FruitTest {
    private Fruit fruit;

    @Before
    public void setUp() {
        fruit = new Fruit("grapes", 10);
    }

    @Test
    public void setName_Ok() {
        fruit.setName("banana");
        assertEquals("banana", fruit.getName());
    }

    @Test
    public void setQuantity_Ok() {
        fruit.setQuantity(100);
        assertEquals(100, fruit.getQuantity());
    }

    @Test
    public void getName_Ok() {
        assertEquals("grapes", fruit.getName());
    }

    @Test
    public void getQuantity_Ok() {
        assertEquals(10, fruit.getQuantity());
    }
}
