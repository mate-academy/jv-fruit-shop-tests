package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationsHandler operationsHandler;

    @Before
    public void setUp() throws Exception {
        Storage.storage.put(new Fruit("banana"), 25);
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationsHandler = new SupplyOperationHandler();
    }

    @Test
    public void supply_validAmountFruits_Ok() {
        FruitDto fruitDto = new FruitDto("s", "banana", 35);
        int expected = 60;
        int actual = new SupplyOperationHandler().apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void return_fruitsInStorage_Ok() {
        FruitDto fruitDto = new FruitDto("r", "banana", 60);
        int expected = 85;
        int actual = new SupplyOperationHandler().apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
