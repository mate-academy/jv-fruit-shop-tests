package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class FruitTest {
    private static final String VALID_STRING = "Fruit{name='banana'}";
    private final Fruit fruitFirst = new Fruit("banana");
    private final Fruit fruitSecond = new Fruit("banana");

    @Test
    public void checkQuality() {
        assertEquals(fruitFirst, fruitSecond);
    }

    @Test
    public void chekSet() {
        fruitFirst.setName("apple");
        assertNotEquals(fruitFirst, fruitSecond);
    }

    @Test
    public void chekGet() {
        assertEquals("banana", fruitSecond.getName());
    }

    @Test
    public void checkToString() {
        assertEquals(fruitSecond.toString(), VALID_STRING);
    }
}
