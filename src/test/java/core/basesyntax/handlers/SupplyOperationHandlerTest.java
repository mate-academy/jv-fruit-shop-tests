package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void setUpFirst() {
        supplyHandler = new SupplyOperationHandler(new StorageDaoImpl());
    }

    @Test
    public void apply_correctDataEmptyStorage_Ok() {
        int expected = 0;
        Fruit orange = new Fruit("orange");
        supplyHandler.doOperation(orange, expected);
        int actual = Storage.storage.get(orange);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_correctData_Ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 0);
        supplyHandler.doOperation(orange, 0);
        int expected = 0;
        int actual = Storage.storage.get(orange);
        assertEquals(expected, actual);
    }

    @Test
    public void apply_validDataFromStorageAfterApply_Ok() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 0);
        supplyHandler.doOperation(orange, 5);
        int actual = Storage.storage.get(orange);
        int expected = 5;
        assertEquals(expected, actual);
    }

    @After
    public void cleanUp() {
        Storage.storage.clear();
    }
}
