package core.basesyntax.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTest {
    private static Fruit someFruit;
    private static Fruit sameFruit;
    private static Fruit anotherFruit;
    private static final String APPLE_NAME = "Apple";

    @BeforeClass
    public static void setUp() {
        someFruit = new Fruit("Apple");
        sameFruit = new Fruit("Apple");
        anotherFruit = new Fruit("Pea");
    }

    @Test
    public void toString_ok() {
        Assert.assertEquals(APPLE_NAME, someFruit.toString());
    }

    @Test
    public void equals_ok() {
        Assert.assertEquals(someFruit, someFruit);
        Assert.assertEquals(someFruit, sameFruit);
    }

    @Test
    public void equals_notOk() {
        Assert.assertNotEquals(someFruit, anotherFruit);
        Assert.assertNotEquals(null, someFruit);
        Assert.assertNotEquals(someFruit, APPLE_NAME);
    }

    @Test
    public void hashCode_ok() {
        Assert.assertEquals(someFruit.hashCode(), someFruit.hashCode());
        Assert.assertEquals(someFruit.hashCode(), sameFruit.hashCode());
    }

    @Test
    public void hashCode_notOk() {
        Assert.assertNotEquals(anotherFruit.hashCode(), someFruit.hashCode());
        Assert.assertNotEquals(anotherFruit.hashCode(), sameFruit.hashCode());
    }
}
