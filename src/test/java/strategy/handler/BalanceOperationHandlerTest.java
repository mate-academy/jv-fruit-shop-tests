package strategy.handler;

import static org.junit.Assert.assertEquals;

import dao.FruitDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String FRUIT_BANANA = "banana";
    private static final int QUANTITY = 100;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler(new FruitDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void add_banana_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_BANANA, QUANTITY);
        balanceOperationHandler.add(fruit);
        assertEquals((Integer) QUANTITY, Storage.fruits.get(FRUIT_BANANA));
    }
}
