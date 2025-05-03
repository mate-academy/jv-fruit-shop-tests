package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private OperationHandler handler;
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        handler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    public void handleBalanceCheck_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100);
        handler.handle(fruitTransaction);
        Integer expected = 100;
        Integer actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }
}
