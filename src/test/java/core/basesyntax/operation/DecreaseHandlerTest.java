package core.basesyntax.operation;

import core.basesyntax.model.Record;
import core.basesyntax.report.FruitBalance;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecreaseHandlerTest {
    private static DecreaseHandler decreaseHandler;

    @BeforeClass
    public static void beforeAll() {
        decreaseHandler = new DecreaseHandler();
    }

    @Test
    public void apply_withStartData_Ok() {
        FruitBalance.FRUIT_BALANCE.put("apple", 20);
        decreaseHandler.apply(new Record("p", "apple", 10));
        int expected = 10;
        int actual = FruitBalance.FRUIT_BALANCE.get("apple");
        Assert.assertEquals(expected, actual);
    }


    @Test (expected = RuntimeException.class)
    public void apply_amountTooBig_NotOk() {
        decreaseHandler.apply(new Record("p", "apple", 10));

    }

    @After
    public void tearDown() {
        FruitBalance.FRUIT_BALANCE.clear();
    }
}