package core.basesyntax;

import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTest {
    private static Fruit fruit;
    private static String expected;

    @BeforeClass
    public static void beforeClass() {
        fruit = new Fruit("banana");
    }

    @Test
    public void toStringFruit_ok() {
        expected = "Fruit{"
                + "fruitName='" + fruit.getFruitName() + '\''
                + '}';
        Assert.assertEquals(expected, fruit.toString());
    }
}
