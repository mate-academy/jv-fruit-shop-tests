package core.basesyntax.service.impl.handlers;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE_FIRST = 100;
    private static final int TEST_BALANCE_SECOND = 70;
    private static final FruitTransaction.Operation TEST_OPERATION = RETURN;
    private static FruitTransaction fruitTransaction;
    private OperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandlerImpl();
        Storage.fruits.put(TEST_FRUIT,TEST_BALANCE_FIRST);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void returnHandler_Work_Ok() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(TEST_OPERATION);
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_BALANCE_SECOND);
        returnOperationHandler.operate(fruitTransaction);
        int expected = TEST_BALANCE_FIRST + TEST_BALANCE_SECOND;
        int actual = Storage.fruits.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }
}
