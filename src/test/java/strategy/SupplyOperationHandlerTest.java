package strategy;

import static org.junit.Assert.assertEquals;

import model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import storage.Storage;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperation;

    @BeforeClass
    public static void beforeClass() {
        supplyOperation = new SupplyOperationHandler();
    }

    @Test
    public void validSupply_Ok() {
        Storage.data.put(new Fruit("banana"), 50);
        supplyOperation.apply(new Fruit("banana"), 50);
        Integer actual = 100;
        assertEquals(actual, Storage.data.get(new Fruit("banana")));
    }

    @After
    public void afterEach() {
        Storage.data.clear();
    }
}
