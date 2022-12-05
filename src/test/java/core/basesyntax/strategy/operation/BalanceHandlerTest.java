package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static String FRUIT = "apple";
    private OperationHandler balanceHandler;
    
    @Before
    public void setUp() {
        balanceHandler = new BalanceHandler();        
    }
    
    @Test
    public void addBalanceHandler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.BALANCE, FRUIT, 20);
        balanceHandler.operate(transaction);
        int expected = 20;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }
    
    @Test
    public void addBalanceHandler_negativeQty_ok() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.BALANCE, FRUIT, -20);
        balanceHandler.operate(transaction);
        int expected = 20;
        int actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }
    
    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
