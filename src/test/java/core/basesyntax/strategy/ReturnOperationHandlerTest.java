package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static ReturnOperationHandler handler;
    private static final FruitTransaction TEST_TRANSACTION = 
            new FruitTransaction("test1", 2, Operation.RETURN);
    private static final FruitTransaction NULL_TRANSACTION = 
            new FruitTransaction(null, null, null);

    @BeforeClass
    public static void beforeClass() {
        handler = new ReturnOperationHandler();
    }

    @Test
    public void handle_makeEntry_Ok() {
        handler.handle(TEST_TRANSACTION);
        Integer actual = Storage.fruitStorage.get("test1");
        assertEquals(Integer.valueOf(2), actual);
    }

    @Test
    public void handle_makeNullEntry_Ok() {
        handler.handle(NULL_TRANSACTION);
        Integer actual = Storage.fruitStorage.get(null);
        assertEquals(Integer.valueOf(0), actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
