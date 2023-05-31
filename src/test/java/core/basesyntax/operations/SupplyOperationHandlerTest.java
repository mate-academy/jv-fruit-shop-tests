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

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void init() {
        Storage.storage.clear();
        operationHandler = new SupplyOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        Storage.storage.put(apple, 100);
    }

    @Test
    public void apply_supplyOperationHandlerFruitExist_ok() {
        operationHandler.apply(new Transaction(Operation.SUPPLY, apple, 5));
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(apple, 105);
        assertEquals(expected, Storage.storage);
    }

    @Test
    public void apply_supplyOperationHandlerNewFruit_ok() {
        Storage.storage.put(banana, 100);
        operationHandler.apply(new Transaction(Operation.SUPPLY, banana, 10));
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(apple, 105);
        expected.put(banana, 110);
        assertEquals(expected, Storage.storage);
    }
}
