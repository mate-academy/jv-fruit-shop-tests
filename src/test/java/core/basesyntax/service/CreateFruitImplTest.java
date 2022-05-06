package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Test;

public class CreateFruitImplTest {
    @Test
    public void createFruit() {
        String expectedName = "apple";
        int expectedAmount = 5;
        String testedName = "apple";
        String testedAmount = "5";
        Fruit expectedFruit = new Fruit();
        expectedFruit.setName(expectedName);
        expectedFruit.setAmount(expectedAmount);
        Fruit fruit = new CreateFruitImpl().createFruit(testedName, testedAmount);
        Assert.assertTrue(expectedFruit.getName().equals(fruit.getName())
                && expectedFruit.getAmount() == (fruit.getAmount()));
    }
}
