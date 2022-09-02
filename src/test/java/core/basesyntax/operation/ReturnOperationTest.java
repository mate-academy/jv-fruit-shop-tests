package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationTest {
    private OperationHandler handler;
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        handler = new ReturnOperationHandler(fruitDao);
    }

    @After
    public void cleanStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void handleReturnCheck_ok() {
        Storage.fruits.put("banana", 150);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 100);
        handler.handle(fruitTransaction);
        Integer expected = 250;
        Integer actual = Storage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleReturnCheck_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 75);
        handler.handle(fruitTransaction);

    }
}
