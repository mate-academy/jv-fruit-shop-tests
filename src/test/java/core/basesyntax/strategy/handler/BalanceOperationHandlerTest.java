package core.basesyntax.strategy.handler;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.*;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @Before
    public void setUp() {
        balanceHandler = new BalanceOperationHandler(new StorageDaoImpl());
    }

    @Test
    public void execute_balanceTransaction_ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(BALANCE, new Fruit("apple"), 5);
        int expected = 5;
        balanceHandler.execute(fruitTransaction);
        int actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void execute_null_ok() {
        balanceHandler.execute(null);
    }
}