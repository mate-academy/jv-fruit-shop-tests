package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.StorageTest;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void setStorageOperation() {
        purchaseHandler = new PurchaseHandler(StorageTest.storage);
        StorageTest.storage.clear();
    }

    @Test
    public void purchaseHandler_checkGettingObject_notOk() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(purchaseHandler.getClass(), actual.getClass());

        fruitDto = new FruitDto("s", "banana", 100);
        actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(purchaseHandler.getClass(), actual.getClass());
    }

    @Test
    public void purchaseHandler_checkGettingObject_ok() {
        FruitDto fruitDto = new FruitDto("p", "banana", 100);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertEquals(purchaseHandler.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_apply_NullCheck_ok() {
        purchaseHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_apply_notEnoughQuantityInStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        purchaseHandler.apply(new FruitDto("p", "banana", 120));
    }

    @Test
    public void purchaseHandler_apply_changeStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        purchaseHandler.apply(new FruitDto("p", "banana", 100));
        int expected = 0;
        int actual = StorageTest.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_constructor_notOk() {
        OperationHandler purchaseHandler = new PurchaseHandler(null);
    }
}
