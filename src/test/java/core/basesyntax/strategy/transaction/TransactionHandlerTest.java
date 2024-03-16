package core.basesyntax.strategy.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TransactionHandlerTest {
    private static final TransactionHandler balanceHandler = new BalanceTransactionHandler();
    private static final TransactionHandler purchaseHandler = new PurchaseTransactionHandler();
    private static final TransactionHandler returnHandler = new ReturnTransactionHandler();
    private static final TransactionHandler supplyHandler = new SupplyTransactionHandler();
    private static final int OLD_QUALITY = 50;
    private static final int TRANSACTION_QUANTITY = 15;

    @Test
    void getTransactionResult_Balance_Ok() {
        int actual = balanceHandler.getTransactionResult(OLD_QUALITY, TRANSACTION_QUANTITY);
        int expected = TRANSACTION_QUANTITY;
        assertEquals(expected, actual, "Result must be - " + expected);
    }

    @Test
    void getTransactionResult_Purchase_Ok() {
        int actual = purchaseHandler.getTransactionResult(OLD_QUALITY, TRANSACTION_QUANTITY);
        int expected = OLD_QUALITY - TRANSACTION_QUANTITY;
        assertEquals(expected, actual, "Result must be - " + expected);
    }

    @Test
    void getTransactionResult_Return_Ok() {
        int actual = returnHandler.getTransactionResult(OLD_QUALITY, TRANSACTION_QUANTITY);
        int expected = OLD_QUALITY + TRANSACTION_QUANTITY;
        assertEquals(expected, actual, "Result must be - " + expected);
    }

    @Test
    void getTransactionResult_Supply_Ok() {
        int actual = supplyHandler.getTransactionResult(OLD_QUALITY, TRANSACTION_QUANTITY);
        int expected = OLD_QUALITY + TRANSACTION_QUANTITY;
        assertEquals(expected, actual, "Result must be - " + expected);
    }
}
