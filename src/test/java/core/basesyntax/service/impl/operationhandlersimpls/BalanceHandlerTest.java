package core.basesyntax.service.impl.operationhandlersimpls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.AfterClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private BalanceHandler balanceHandler = new BalanceHandler();
    private FruitRecord fruitRecord = new FruitRecord("b", new Fruit("banana"), 10);

    @Test(expected = NullPointerException.class)
    public void apply_null_Ok() {
        balanceHandler.apply(null);
    }

    @Test
    public void apply_validData_Ok() {
        int expected = 10;
        int actual = balanceHandler.apply(fruitRecord);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}
