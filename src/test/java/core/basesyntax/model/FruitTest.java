package core.basesyntax.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FruitTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int QUANTITY = 100;
    private static final int NEW_QUANTITY = 123;
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
        long actual = fruit.getBalance();
        assertEquals(QUANTITY, actual);
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
