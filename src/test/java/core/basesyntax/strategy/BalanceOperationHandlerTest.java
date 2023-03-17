package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final int INITIAL_QUANTITY1 = 20;
    private static final int INITIAL_QUANTITY2 = 10;
    private static final String FRUIT_NAME = "apple";
    private OperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    void adds_toStorage_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY1);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(INITIAL_QUANTITY1, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, INITIAL_QUANTITY2);
        assertThrows(RuntimeException.class, () ->
                balanceOperationHandler.handle(fruitTransactionWithNullFruit));
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, INITIAL_QUANTITY2);
        assertThrows(RuntimeException.class, () ->
                balanceOperationHandler.handle(fruitTransactionWithNullOperation));
    }

    @Test
    void handle_multipleTransactions_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY1);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY2);
        balanceOperationHandler.handle(firstTransaction);
        balanceOperationHandler.handle(secondTransaction);
        assertEquals(INITIAL_QUANTITY2, Storage.storage.get(FRUIT_NAME));
    }
}
