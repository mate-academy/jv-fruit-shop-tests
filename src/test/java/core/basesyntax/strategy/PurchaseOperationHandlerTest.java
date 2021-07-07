package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationsHandler operationsHandler;
    private static FruitDto fruitDto;

    @BeforeClass
    public static void beforeClass() {
        operationsHandler = new PurchaseOperationHandler();
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void purchase_validAmountFruits_Ok() {
        fruitDto = new FruitDto("p", "apple", 20);
        int expected = 80;
        int actual = new PurchaseOperationHandler().apply(fruitDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_invalidAmountFruits_Ok() {
        fruitDto = new FruitDto("p", "apple", 102);
        new PurchaseOperationHandler().apply(fruitDto);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
