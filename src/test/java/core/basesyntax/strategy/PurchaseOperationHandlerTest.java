package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationsHandler operationsHandler;

    @Before
    public void setUp() throws Exception {
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @BeforeClass
    public static void beforeClass() {
        operationsHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchase_validAmountFruits_Ok() {
        FruitDto fruitDto = new FruitDto("p", "apple", 20);
        int expected = 80;
        int actual = operationsHandler.apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_invalidAmountFruits_Ok() {
        FruitDto fruitDto = new FruitDto("p", "apple", 102);
        new PurchaseOperationHandler().apply(fruitDto);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
