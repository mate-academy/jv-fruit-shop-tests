package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.StorageTest;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    @Before
    public void setStorageOperation() {
        StorageTest.storage.clear();
    }

    @Test
    public void purchaseHandler_checkGettingObject_notOk() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        OperationHandler expected = new PurchaseHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());

        fruitDto = new FruitDto("s", "banana", 100);
        actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void purchaseHandler_checkGettingObject_ok() {
        FruitDto fruitDto = new FruitDto("p", "banana", 100);
        OperationHandler expected = new PurchaseHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_apply_NullCheck_ok() {
        OperationHandler operationHandler = new PurchaseHandler(StorageTest.storage);
        operationHandler.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_apply_notEnoughQuantityInStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        OperationHandler purchaseHandler = new PurchaseHandler(StorageTest.storage);
        purchaseHandler.apply(new FruitDto("p", "banana", 120));
    }

    @Test
    public void purchaseHandler_apply_changeStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        OperationHandler purchaseHandler = new PurchaseHandler(StorageTest.storage);
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
