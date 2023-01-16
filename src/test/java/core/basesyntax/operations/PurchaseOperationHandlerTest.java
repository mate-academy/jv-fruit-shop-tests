package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        Storage.storage.put(apple, 100);
    }

    public void apply_purchaseOperation_Ok() {
        Storage.storage.put(banana, 100);
        operationHandler.apply(new Transaction(Operation.PURCHASE, apple, 20));
        operationHandler.apply(new Transaction(Operation.PURCHASE, banana, 20));
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(apple, 80);
        expected.put(banana, 80);
        assertEquals(expected, Storage.storage);
    }
}
