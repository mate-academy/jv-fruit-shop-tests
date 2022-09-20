package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BalanceTransactionHandlerTest {
    private static final TransactionHandler transactionHandler = new BalanceTransactionHandler();
    private static final int FIRST_VALUE = 5;
    private static final int SECOND_VALUE = 10;

    @Test
    void transactionValue_ok() {
        int result = transactionHandler.getTransactionResult(FIRST_VALUE, SECOND_VALUE);
        assertEquals(SECOND_VALUE, result);
    }
}
