package core.basesyntax.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTest {
    private static final String FRUIT_NAME = "apple";
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        fruit = new Fruit(FRUIT_NAME);
    }

    @Test
    public void getFruitName_Ok() {
        fruit = new Fruit(FRUIT_NAME);
        String actual = fruit.getName();
        Assert.assertEquals(FRUIT_NAME, actual);
    }

    @Test
    public void compareTwoFruitsEquals_Ok() {
        Assert.assertEquals(new Fruit("apple"), fruit);
    }
}
