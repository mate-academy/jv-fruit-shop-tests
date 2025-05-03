package core.basesyntax.service.impl.handlers;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE_FIRST = 300;
    private static final int TEST_BALANCE_SECOND = 120;
    private static final FruitTransaction.Operation TEST_OPERATION = RETURN;
    private FruitTransaction fruitTransaction;
    private OperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandlerImpl();
        Storage.fruits.put(TEST_FRUIT,TEST_BALANCE_FIRST);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void supplyHandler_Work_Ok() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(TEST_OPERATION);
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_BALANCE_SECOND);
        supplyOperationHandler.operate(fruitTransaction);
        int expected = TEST_BALANCE_FIRST + TEST_BALANCE_SECOND;
        int actual = Storage.fruits.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }
}
