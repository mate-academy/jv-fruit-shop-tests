package core.basesyntax.service.transactions;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTransactionHandlerTest {
    private static PurchaseTransactionHandler purchaseTransactionHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseTransactionHandler = new PurchaseTransactionHandler();
    }

    @Test
    public void purchaseTransaction_Ok() {
        int expected = 0;
        int transaction = purchaseTransactionHandler.getCurrentQuantity(10, 10);
        assertEquals(expected, transaction);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseTransaction_Null_NotOk() {
        purchaseTransactionHandler.getCurrentQuantity(null, 10);
    }
}
