package core.basesyntax;

import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class FruitTest {
    @Test
    public void equals_fruitIsNull_NotOk() {
        Fruit fruit0 = new Fruit("apple");
        Fruit fruit1 = new Fruit("banana");
        Assert.assertNotEquals(fruit0, fruit1);
    }
}
