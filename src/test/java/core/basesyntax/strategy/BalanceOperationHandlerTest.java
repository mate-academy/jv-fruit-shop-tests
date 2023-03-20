package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
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
                        20);
        balanceOperationHandler.handle(fruitTransaction);
        assertEquals(20, (int) Storage.storage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10);
        balanceOperationHandler.handle(fruitTransactionWithNullFruit);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullOperation_notOk() {
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, 10);
        balanceOperationHandler.handle(fruitTransactionWithNullOperation);
    }

    @Test
    public void handle_multipleTransactions_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_NAME,
                        20);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_NAME,
                        10);
        balanceOperationHandler.handle(firstTransaction);
        balanceOperationHandler.handle(secondTransaction);
        assertEquals(10, (int) Storage.storage.get(FRUIT_NAME));
    }
}
