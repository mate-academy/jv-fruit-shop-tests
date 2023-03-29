package core.basesyntax.service.transactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

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

    @Test(expected = RuntimeException.class)
    public void returnTransaction_Null_NotOk() {
        returnTransactionHandler.getCurrentQuantity(null, 10);
    }
}
