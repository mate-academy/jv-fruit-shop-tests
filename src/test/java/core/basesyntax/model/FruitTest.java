package core.basesyntax.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class FruitTest {

    @Test
    public void equals_fruit_Ok() {
        Fruit apple1 = new Fruit("apple", 10);
        Fruit apple2 = new Fruit("apple", 10);
        assertEquals(apple1, apple2);
        assertEquals(apple1.hashCode(), apple2.hashCode());
    }

    @Test
    public void equals_fruit_notOk() {
        Fruit apple1 = new Fruit("apple", 10);
        Fruit apple2 = new Fruit("apple", 20);
        assertNotEquals(apple1, apple2);
    }
}