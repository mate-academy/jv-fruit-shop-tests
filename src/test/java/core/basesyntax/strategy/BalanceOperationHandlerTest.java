package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationsHandler operationsHandler;
    private static FruitDto fruitDto;

    @BeforeClass
    public static void beforeClass() {
        operationsHandler = new BalanceOperationHandler();
        Storage.storage.put(new Fruit("banana"), 60);
    }

    @Test
    public void getBalance_Ok() {
        fruitDto = new FruitDto("b", "banana", 60);
        int expected = 60;
        int actual = new BalanceOperationHandler().apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
