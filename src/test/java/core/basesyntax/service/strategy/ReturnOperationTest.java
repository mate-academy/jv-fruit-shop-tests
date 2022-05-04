package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineData;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setup() {
        operationHandler = new ReturnOperation();
    }

    @Test
    public void returnOperation_Correct_ok() {
        Fruit apple = new Fruit("apple");
        Storage.store.put(apple, 27);
        LineData lineData = new LineData("r",apple, 12);
        operationHandler.operate(lineData);
        int expected = 39;
        int actual = Storage.store.get(apple);
        Assert.assertEquals(expected, actual);
    }
}
