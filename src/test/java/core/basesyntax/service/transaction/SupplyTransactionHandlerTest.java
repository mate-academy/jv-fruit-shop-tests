package core.basesyntax.service.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SupplyTransactionHandlerTest {
    private static final int FIRST_VALUE = 5;
    private static final int SECOND_VALUE = 10;
    private static TransactionHandler transactionHandler;

    @Before
    public void setUp() {
        transactionHandler = new SupplyTransactionHandler();
    }

    @Test
    public void transactionValue_ok() {
        int result = transactionHandler.getTransactionResult(FIRST_VALUE, SECOND_VALUE);
        assertEquals(FIRST_VALUE + SECOND_VALUE, result);
    }
}
