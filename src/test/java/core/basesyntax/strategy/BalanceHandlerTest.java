package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.StorageTest;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    @Before
    public void setUp() {
        StorageTest.storage.clear();
    }

    @Test
    public void balanceHandler_checkGettingObject_notOk() {
        FruitDto fruitDto = new FruitDto("s", "banana", 100);
        OperationHandler expected = new BalanceHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());

        fruitDto = new FruitDto("p", "banana", 100);
        actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void balanceHandler_checkGettingObject_ok() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        OperationHandler expected = new BalanceHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void balanceHandler_apply_NullCheck_ok() {
        OperationHandler operationHandler = new BalanceHandler(StorageTest.storage);
        operationHandler.apply(null);
    }

    @Test
    public void balanceHandler_apply_changeStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        OperationHandler purchaseHandler = new BalanceHandler(StorageTest.storage);
        purchaseHandler.apply(new FruitDto("b", "grape", 100));
        assertTrue(StorageTest.storage.containsKey(new Fruit("grape")));
        int expected = 100;
        int actual = StorageTest.storage.get(new Fruit("grape"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_constructor_notOk() {
        OperationHandler purchaseHandler = new BalanceHandler(null);
    }
}
