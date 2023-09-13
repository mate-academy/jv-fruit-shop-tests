package core.basesyntax.strategy.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceTransactionHandlerTest {
    private static final String NORMAL_FRUIT_NAME = "banana";
    private static final int NORMAL_FRUIT_QUANTITY = 20;
    private static final FruitTransaction transaction =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    NORMAL_FRUIT_NAME, NORMAL_FRUIT_QUANTITY);
    private static TransactionHandler transactionHandler;

    @BeforeAll
    static void beforeAll() {
        transactionHandler = new BalanceTransactionHandler();
    }

    @Test
    void handle_validData_ok() {
        transactionHandler.handle(transaction);
        assertFalse(Storage.fruitMap.isEmpty());
        Integer actual = Storage.fruitMap.get(NORMAL_FRUIT_NAME);
        assertEquals(NORMAL_FRUIT_QUANTITY, actual);
    }

    @Test
    void handle_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> transactionHandler.handle(null));
    }
}
