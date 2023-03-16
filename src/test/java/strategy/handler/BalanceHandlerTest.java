package strategy.handler;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strategy.TransactionHandler;

public class BalanceHandlerTest {
    private static final String FRUIT_FOR_TEST = "banana";
    private static final Integer FRUIT_QUANTITY = 5;
    private static TransactionHandler balanceHandler;
    private static FruitTransaction balance;

    @Before
    public void setUp() {
        balanceHandler = new BalanceHandler();

        balance = new FruitTransaction();
        balance.setOperation(FruitTransaction.Operation.BALANCE);
        balance.setFruit(FRUIT_FOR_TEST);
        balance.setQuantity(5);
    }

    @Test
    public void handle_BalanceHandle_Ok() {
        Integer expected = FRUIT_QUANTITY;
        balanceHandler.handle(balance);
        Integer actual = Storage.fruits.get(FRUIT_FOR_TEST);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
