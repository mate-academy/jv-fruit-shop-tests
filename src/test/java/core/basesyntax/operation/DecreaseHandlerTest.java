package core.basesyntax.operation;

import core.basesyntax.model.Record;
import core.basesyntax.report.FruitBalance;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecreaseHandlerTest {
    private static Record record;
    private static DecreaseHandler decreaseHandler;

    @BeforeClass
    public static void beforeAll() {
        decreaseHandler = new DecreaseHandler();
        record = new Record("p", "apple", 10);
    }

    @Test
    public void apply_purchaseCorrectAmount_Ok() {
        FruitBalance.FRUIT_BALANCE.put("apple", 20);
        decreaseHandler.apply(record);
        int expected = 10;
        int actual = FruitBalance.FRUIT_BALANCE.get("apple");
        Assert.assertEquals("Fruit amounts should match!", expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_purchaseNoInitialBalance_NotOk() {
        decreaseHandler.apply(record);
    }

    @After
    public void tearDown() {
        FruitBalance.FRUIT_BALANCE.clear();
    }
}
