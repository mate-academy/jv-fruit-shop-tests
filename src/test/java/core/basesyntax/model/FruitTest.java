package core.basesyntax.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FruitTest {
    private static final String VALID_STRING = "Fruit{name='banana'}";
    Fruit fruitFirst = new Fruit("banana");
    Fruit fruitSecond = new Fruit("banana");

    @Test
    public void checkQuality () {
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
        System.out.println(fruitSecond.toString());
        assertEquals(fruitSecond.toString(), VALID_STRING);
    }
}