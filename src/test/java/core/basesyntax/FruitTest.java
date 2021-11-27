package core.basesyntax;

import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Test;

public class FruitTest {

    private static Fruit fruit;

    @Test
    public void testFruit() {
        fruit = new Fruit("apple");
        Assert.assertEquals("apple", fruit.getName());
        fruit.setName("banana");
        Assert.assertEquals("banana", fruit.getName());
    }

}
