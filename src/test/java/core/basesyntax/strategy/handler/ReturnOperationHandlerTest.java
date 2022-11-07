package core.basesyntax.strategy.handler;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.Assert.*;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;

    @Before
    public void setUp() throws Exception {
        returnHandler = new ReturnOperationHandler(new StorageDaoImpl());
        Storage.storage.put(new Fruit("apple"), 10);
    }

    @Test
    public void execute_returnTransaction_ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(RETURN, new Fruit("apple"), 5);
        int expected = 15;
        returnHandler.execute(fruitTransaction);
        int actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void execute_null_ok() {
        returnHandler.execute(null);
    }
}