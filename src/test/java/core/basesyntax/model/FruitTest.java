package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final long QUANTITY = 100;
    private static final long NEW_QUANTITY = 123;
    private static Fruit fruit;

    @BeforeClass
    public static void setUp() {
        fruit = new Fruit(APPLE, QUANTITY);
    }

    @Test
    public void getNameTest_Ok() {
        String actual = fruit.getName();
        assertEquals(APPLE, actual);
    }

    @Test
    public void getBalanceTest_Ok() {
        fruit.setBalance(QUANTITY);
        assertEquals(QUANTITY, fruit.getBalance());
    }

    @Test
    public void setBalanceTest_Ok() {
        fruit.setBalance(NEW_QUANTITY);
        assertEquals(NEW_QUANTITY, fruit.getBalance());
    }

    @Test
    public void equalsTest_Ok() {
        assertEquals(fruit, new Fruit(APPLE, QUANTITY));
        assertNotEquals(fruit, new Fruit(BANANA, QUANTITY));
    }
}
