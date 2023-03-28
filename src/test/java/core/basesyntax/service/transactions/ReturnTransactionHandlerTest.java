package core.basesyntax.service.transactions;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReturnTransactionHandlerTest {
    private static ReturnTransactionHandler returnTransactionHandler;

    @BeforeClass
    public static void beforeClass() {
        returnTransactionHandler = new ReturnTransactionHandler();
    }

    @Test
    public void returnTransaction_Ok() {
        int expected = 20;
        int transaction = returnTransactionHandler.getCurrentQuantity(10, 10);
        assertEquals(expected, transaction);
    }

    @Test
    public void returnTransaction_Null_NotOk() {
        try {
            returnTransactionHandler.getCurrentQuantity(null, 10);
        } catch (RuntimeException e) {
            return;
        }
        fail("ReturnTransaction should not be able work with null");
    }
}
