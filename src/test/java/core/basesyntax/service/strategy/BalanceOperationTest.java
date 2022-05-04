package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineData;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setup() {
        operationHandler = new BalanceOperation();
    }

    @Test
    public void balanceOperation_Correct_ok() {
        Fruit apple = new Fruit("apple");
        Storage.store.put(apple, 25);
        LineData lineData = new LineData("b",apple, 12);
        operationHandler.operate(lineData);
        int expected = 12;
        int actual = Storage.store.get(apple);
        Assert.assertEquals(expected, actual);
    }
}
