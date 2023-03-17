package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final int INITIAL_QUANTITY1 = 20;
    private static final int INITIAL_QUANTITY2 = 10;
    private static final String FRUIT_NAME = "apple";
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void adds_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY1);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(INITIAL_QUANTITY1, (int) Storage.storage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, INITIAL_QUANTITY2);
        balanceOperationHandler.handle(fruitTransactionWithNullFruit);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullOperation_notOk() {
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, INITIAL_QUANTITY2);
        balanceOperationHandler.handle(fruitTransactionWithNullOperation);
    }

    @Test
    public void handle_multipleTransactions_ok() {
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
        assertEquals(INITIAL_QUANTITY2, (int) Storage.storage.get(FRUIT_NAME));
    }
}
