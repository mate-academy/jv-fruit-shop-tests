package core.basesyntax;

import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Test;

public class FruitModelTest {

    @Test
    public void checkFruitsHashCodeEquality_hashCode_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("banana");
        int expected = fruit.hashCode();
        int actual = newFruit.hashCode();
        Assert.assertEquals("Test failed! Fruits hashcodes should be equal!",
                expected, actual);
    }

    @Test
    public void checkFruitsHashCodeEquality_hashCode_Not_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("apple");
        int expected = fruit.hashCode();
        int actual = newFruit.hashCode();
        Assert.assertNotEquals("Test failed! Fruits hashcodes should be different!",
                expected, actual);
    }

    @Test
    public void checkFruitsEquality_equals_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("banana");
        Assert.assertEquals("Test failed! Fruits should be equal!",
                fruit, newFruit);
    }

    @Test
    public void checkFruitsEquality_equals_Not_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("apple");
        Assert.assertNotEquals("Test failed! Fruits should be equal!",
                fruit, newFruit);
    }
}
