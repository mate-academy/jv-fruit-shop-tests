package core.basesyntax.strategy.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseTransactionHandlerTest {
    private static final String NORMAL_FRUIT_NAME = "banana";
    private static final int BALANCE_FRUIT_QUANTITY = 120;
    private static final int PURCHASE_FRUIT_QUANTITY = 13;
    private static final int OVER_PURCHASE_FRUIT_QUANTITY = 121;

    private static final FruitTransaction transaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    NORMAL_FRUIT_NAME, PURCHASE_FRUIT_QUANTITY);

    private static final FruitTransaction overTransaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    NORMAL_FRUIT_NAME, OVER_PURCHASE_FRUIT_QUANTITY);

    private static TransactionHandler transactionHandler;

    @BeforeAll
    static void beforeAll() {
        transactionHandler = new PurchaseTransactionHandler();
    }

    @Test
    void handle_normalPurchaseFruit_ok() {
        Storage.fruitMap.put(NORMAL_FRUIT_NAME, BALANCE_FRUIT_QUANTITY);
        transactionHandler.handle(transaction);
        Integer actual = Storage.fruitMap.get(NORMAL_FRUIT_NAME);
        assertEquals(BALANCE_FRUIT_QUANTITY - PURCHASE_FRUIT_QUANTITY, actual);

    }

    @Test
    void handle_overPurchaseFruit_notOk() {
        Storage.fruitMap.put(NORMAL_FRUIT_NAME, BALANCE_FRUIT_QUANTITY);
        transactionHandler.handle(transaction);
        assertThrows(RuntimeException.class, () -> transactionHandler.handle(overTransaction));
    }

    @Test
    void handle_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> transactionHandler.handle(null));
    }
}
