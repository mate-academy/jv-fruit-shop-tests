package core.basesyntax.strategy.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnTransactionHandlerTest {
    private static final String NORMAL_FRUIT_NAME = "apple";
    private static final int BALANCE_FRUIT_QUANTITY = 100;
    private static final int RETURN_FRUIT_QUANTITY = 10;
    private static final FruitTransaction transaction =
            new FruitTransaction(FruitTransaction.Operation.RETURN,
                    NORMAL_FRUIT_NAME, RETURN_FRUIT_QUANTITY);
    private static TransactionHandler transactionHandler;

    @BeforeAll
    static void beforeAll() {
        transactionHandler = new ReturnTransactionHandler();
    }

    @Test
    void handle_validData_ok() {
        Storage.fruitMap.put(NORMAL_FRUIT_NAME, BALANCE_FRUIT_QUANTITY);
        transactionHandler.handle(transaction);
        Integer actual = Storage.fruitMap.get(NORMAL_FRUIT_NAME);
        assertEquals(BALANCE_FRUIT_QUANTITY + RETURN_FRUIT_QUANTITY, actual);
    }

    @Test
    void handle_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> transactionHandler.handle(null));
    }
}
