package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationsHandler operationsHandler;

    @Before
    public void setUp() throws Exception {
        Storage.storage.put(new Fruit("banana"), 60);
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationsHandler = new BalanceOperationHandler();
    }

    @Test
    public void getBalance_Ok() {
        FruitDto fruitDto = new FruitDto("b", "banana", 60);
        int expected = 60;
        int actual = operationsHandler.apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
