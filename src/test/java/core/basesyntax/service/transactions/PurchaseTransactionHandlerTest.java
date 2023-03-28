package core.basesyntax.service.transactions;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void purchaseTransaction_Null_NotOk() {
        try {
            purchaseTransactionHandler.getCurrentQuantity(null, 10);
        } catch (RuntimeException e) {
            return;
        }
        fail("PurchaseTransactionHandler should not be able work with null");
    }
}
