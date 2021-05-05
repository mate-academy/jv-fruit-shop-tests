package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class FruitTest {
    @Test
    public void fruitCreateNew_Ok() {
        Fruit fruit = new Fruit("Apple");
        Fruit expected = new Fruit("Apple");
        assertEquals(fruit, expected);
    }

    @Test
    public void fruitCreateNew_NotOk() {
        Fruit fruit = new Fruit("Apple");
        Fruit expected = new Fruit("Orange");
        assertNotEquals(fruit, expected);
    }
}
