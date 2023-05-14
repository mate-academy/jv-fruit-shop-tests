package core.basesyntax.service.transactions;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyTransactionHandlerTest {
    private static SupplyTransactionHandler supplyTransactionHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyTransactionHandler = new SupplyTransactionHandler();
    }

    @Test
    public void supplyTransaction_Ok() {
        int expected = 20;
        int transaction = supplyTransactionHandler.getCurrentQuantity(10, 10);
        assertEquals(expected, transaction);
    }

    @Test(expected = RuntimeException.class)
    public void supplyTransaction_Null_NotOk() {
        supplyTransactionHandler.getCurrentQuantity(null, 10);
    }
}
