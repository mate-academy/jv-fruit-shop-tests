package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String FRUIT = "apple";
    private OperationHandler balanceHandler;

    @Before
    public void setUp() {
        balanceHandler = new BalanceOperationHandler();
    }

    @Test
    public void handle_balanceHandle_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.BALANCE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(20);
        balanceHandler.handle(transaction);
        Integer expected = 20;
        Integer actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
