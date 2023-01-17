package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;

    @BeforeClass
    public static void init() {
        Storage.storage.clear();
        operationHandler = new ReturnOperationHandler();
        apple = new Fruit("apple");
        Storage.storage.put(apple, 100);
    }

    @Test
    public void apply_returnOperationHandler_ok() {
        operationHandler.apply(new Transaction(Operation.RETURN, apple, 5));
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(apple, 105);
        assertEquals(expected, Storage.storage);
    }
}
