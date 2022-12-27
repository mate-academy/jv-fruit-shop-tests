package core.basesyntax.service.impl.handlers;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static final FruitTransaction.Operation TEST_OPERATION = BALANCE;
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE = 50;
    private FruitTransaction fruitTransaction;
    private OperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandlerImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(TEST_OPERATION);
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_BALANCE);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceHandler_Work_Ok() {
        balanceOperationHandler.operate(fruitTransaction);
        int expected = 50;
        int actual = Storage.fruits.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }
}
