package core.basesyntax.service.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PurchaseTransactionHandlerTest {
    private static final TransactionHandler transactionHandler = new PurchaseTransactionHandler();
    private static final int FIRST_VALUE = 5;
    private static final int SECOND_VALUE = 10;

    @Test
    public void transactionValue_ok() {
        int result = transactionHandler.getTransactionResult(FIRST_VALUE, SECOND_VALUE);
        assertEquals(FIRST_VALUE - SECOND_VALUE, result);
    }
}
