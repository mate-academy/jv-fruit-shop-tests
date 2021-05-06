package core.basesyntax.shop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTest {
    private static final String NAME = "apple";
    private static final String COMPARED_NAME = "cucumber";
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        fruit = new Fruit(NAME);
    }

    @Test
    public void getName_Ok() {
        String actual = fruit.getName();
        assertEquals(NAME, actual);
    }

    @Test
    public void testEquals_Ok() {
        assertEquals(fruit, new Fruit(NAME));
        assertEquals(fruit, fruit);
        assertNotEquals(null, fruit);
        assertNotEquals(fruit, "");
    }

    @Test
    public void compareToVegetables_Ok() {
        int actual = fruit.compareTo(
                new Fruit(COMPARED_NAME));
        assertEquals(-2, actual);
    }

    @Test
    public void compareToSame_Ok() {
        int actual = fruit.compareTo(
                new Fruit(NAME));
        assertEquals(0, actual);
    }
}
