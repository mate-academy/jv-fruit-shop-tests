package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String FRUIT = "banana";
    private static final int EXPECTED = 20;
    private static FruitTransaction transaction;
    private static BalanceOperationHandler balanceOperation;

    @BeforeClass
    public static void beforeAll() throws Exception {
        Storage.fruits.clear();
        transaction = creteTransaction(FruitTransaction.Operation.BALANCE, FRUIT, EXPECTED);
        balanceOperation = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperation_handle_Ok() {
        balanceOperation.handle(transaction);
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(EXPECTED, actual);
    }

    @Test(expected = RuntimeException.class)
    public void balanceOperation_notFoundInStorage_NotOk() {
        balanceOperation.handle(null);
    }

    @After
    public void after() {
        Storage.fruits.clear();
    }

    private static FruitTransaction creteTransaction(FruitTransaction.Operation operation,
                                                     String fruitName,
                                                     int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
