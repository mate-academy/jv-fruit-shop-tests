package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitCreatorImplTest {
    private static FruitCreator fruitCreator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitCreator = new FruitCreatorImpl();
    }

    @Test
    public void createFruit_Ok() {
        String expectedName = "apple";
        int expectedAmount = 5;
        String testedName = "apple";
        String testedAmount = "5";
        Fruit expectedFruit = new Fruit();
        expectedFruit.setName(expectedName);
        expectedFruit.setAmount(expectedAmount);
        Fruit fruit = fruitCreator.createFruit(testedName, testedAmount);
        Assert.assertTrue("Test failed! Expected Fruit: " + expectedFruit,
                expectedFruit.getName().equals(fruit.getName())
                && expectedFruit.getAmount() == (fruit.getAmount()));
    }
}
