package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class FruitServiceImplTest {

    private static FruitService fruitService;

    @Test
    public void testFruitService() {
        fruitService = new FruitServiceImpl();
        Fruit fruit = fruitService.createNewFruit("banana");
        Assert.assertTrue(Fruit.class.equals(fruit.getClass()));
    }

}
