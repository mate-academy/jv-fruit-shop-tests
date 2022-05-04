package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineData;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setup() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaiseOperation_buyCorrect_ok() {
        Fruit apple = new Fruit("apple");
        Storage.store.put(apple, 25);
        LineData lineData = new LineData("p",apple, 12);
        operationHandler.operate(lineData);
        int expected = 13;
        int actual = Storage.store.get(apple);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaiseOperation_buyMoreThanAvailable_notOk() {
        Fruit apple = new Fruit("apple");
        Storage.store.put(apple, 5);
        LineData lineData = new LineData("p",apple, 8);
        operationHandler.operate(lineData);
    }
}
