package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineData;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setup() {
        operationHandler = new SupplyOperation();
    }

    @Test
    public void supplyOperation_Correct_ok() {
        Fruit apple = new Fruit("apple");
        Storage.store.put(apple, 9);
        LineData lineData = new LineData("s",apple, 9);
        operationHandler.operate(lineData);
        int expected = 18;
        int actual = Storage.store.get(apple);
        Assert.assertEquals(expected, actual);
    }
}
