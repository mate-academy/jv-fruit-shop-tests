package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private static String FRUIT = "apple";
    private OperationHandler returnHandler;

    @Before
    public void setUp() {
        returnHandler = new ReturnHandler();
    }

    @Test
    public void addReturnHandler() {
        Storage.fruits.put(FRUIT, 0);
        FruitTransaction returnTransaction = new FruitTransaction(
                Operation.getByCode("r"), FRUIT, 20);
        returnHandler.operate(returnTransaction);
        int expected = 20;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }
    
    @Test
    public void addReturnHandler_negativeQty_ok() {
        Storage.fruits.put(FRUIT, 0);
        FruitTransaction returnTransaction = new FruitTransaction(
                Operation.getByCode("r"), FRUIT, -20);
        returnHandler.operate(returnTransaction);
        int expected = 20;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }
    
    @Test(expected = RuntimeException.class)
    public void addReturnHandler_notExistProduct_notOk() {
        FruitTransaction returnTransaction = new FruitTransaction(
                Operation.getByCode("r"), FRUIT, 20);
        returnHandler.operate(returnTransaction);
    }
     
    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
