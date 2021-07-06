package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
    }

    @Test
    public void balanceHandler_addTransaction_Ok() {
        FruitDto fruitDto = new FruitDto("b", "apple", 18);
        handler.apply(fruitDto);
        int actual = Storage.storage.get(apple);
        int expected = 18;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceHandler_chekReturnValue_Ok() {
        FruitDto fruitDto = new FruitDto("b", "banana", 84);
        int actual = handler.apply(fruitDto);
        int expected = 84;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void erase() {
        Storage.storage.clear();
    }
}
