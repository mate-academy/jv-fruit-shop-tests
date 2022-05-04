package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class SupplyOperationHandlerTest {

    private static OperationHandler supplyOperation;
    private static Map<Fruit, Integer> storage;

    @BeforeClass
    public static void beforeClass() {
        supplyOperation = new SupplyOperationHandler();
        storage = Storage.data;
    }

    @Test
    public void validSupply_Ok() {
        storage.put(new Fruit("banana"), 50);
        supplyOperation.apply(new Fruit("banana"), 50);
        assertTrue(storage.containsKey(new Fruit("banana")));
        assertTrue(storage.containsValue(100));
        assertEquals(1, storage.size());
    }

    @After
    public void afterEach() {
        storage.clear();
    }
}
