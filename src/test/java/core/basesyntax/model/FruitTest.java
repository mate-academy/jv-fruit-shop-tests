package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTest {
    private static final String BANANA_NAME = "banana";
    private static final String APPLE_NAME = "apple";
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() throws Exception {
        banana = new Fruit("banana");
    }

    @Test
    public void equalsTest_returnsTrue() {
        assertEquals(banana, new Fruit("banana"));
        assertEquals(banana, banana);
        assertSame(banana, banana);
        assertNotEquals(new Fruit("apple"), banana);
        assertNotEquals(banana, null);
    }

    @Test
    public void getName_sameName_returnsTrue() {
        assertEquals(banana.getName(), BANANA_NAME);
    }

    @Test
    public void getName_differentName_returnsTrue() {
        assertNotEquals(banana.getName(), APPLE_NAME);
    }
}
