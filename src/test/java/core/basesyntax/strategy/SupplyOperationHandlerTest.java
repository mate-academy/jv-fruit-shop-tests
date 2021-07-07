package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationsHandler operationsHandler;
    private static FruitDto fruitDto;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationsHandler = new SupplyOperationHandler();
        Storage.storage.put(new Fruit("banana"), 25);
    }

    @Test
    public void supply_validAmountFruits_Ok() {
        fruitDto = new FruitDto("s", "banana", 35);
        int expected = 60;
        int actual = new SupplyOperationHandler().apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}
