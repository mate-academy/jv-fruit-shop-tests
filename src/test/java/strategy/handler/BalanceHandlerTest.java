package strategy.handler;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class BalanceHandlerTest {
    private static TransactionHandler balanceHandler;
    private static FruitTransaction balance;

    @Before
    public void setUp() {
        balanceHandler = new BalanceHandler();

        balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit("banana");
        balance.setQuantity(5);
    }

    @Test
    public void handle_BalanceHandle_Ok() {
        Integer expected = 5;
        balanceHandler.handle(balance);
        Integer actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
