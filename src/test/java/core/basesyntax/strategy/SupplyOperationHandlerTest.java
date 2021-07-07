package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit banana;
    private static Fruit orange;

    @BeforeClass
    public static void beforeClass() {
        handler = new SupplyOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        Storage.storage.put(apple, 35);
        Storage.storage.put(banana, 8);
    }

    @Test
    public void supplyHandler_addTransaction_Ok() {
        FruitDto fruitDto = new FruitDto("s", "banana", 84);
        handler.apply(fruitDto);
        int expected = 92;
        int actual = Storage.storage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyHandler_checkReturnTransaction_Ok() {
        FruitDto fruitDto = new FruitDto("r", "apple", 20);
        int expected = 55;
        int actual = handler.apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @Before
    public void setUp() {
        Storage.storage.put(apple, 35);
    }

    @Test
    public void supplyHandler_checkReturnValue_Ok() {
        FruitDto fruitDto = new FruitDto("s", "apple", 1);
        int expected = 36;
        int actual = handler.apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }
}
