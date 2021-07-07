package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dbtest.StorageTest;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class AdditionHandlerTest {
    public static final String PATH_INPUT_FILE =
            "src/test/resources/additiontest/storageAdditionTest.csv";
    public static final String PATH_OUTPUT_FILE =
            "src/test/resources/additiontest/resultAdditionTest.csv";
    public static final String PATH_FILE_RESULT =
            "src/test/resources/additiontest/reportAdditionTest.csv";

    @Before
    public void clearStorage() {
        StorageTest.storage.clear();
    }

    @Test
    public void additionHandler_checkGettingObject_notOk() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        OperationHandler expected = new AdditionHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());

        fruitDto = new FruitDto("p", "banana", 100);
        actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertNotEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void additionHandler_checkGettingObject_ok() {
        FruitDto fruitDto = new FruitDto("r", "banana", 100);
        OperationHandler expected = new AdditionHandler(StorageTest.storage);
        OperationHandler actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertEquals(expected.getClass(), actual.getClass());

        fruitDto = new FruitDto("s", "banana", 100);
        actual = StorageTest.operationHandlerMap.get(fruitDto.getOperation());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void additionHandler_apply_NullCheck_ok() {
        OperationHandler operationHandler = new AdditionHandler(StorageTest.storage);
        operationHandler.apply(null);
    }

    @Test
    public void additionHandler_apply_changeStorage_ok() {
        StorageTest.storage.put(new Fruit("banana"), 100);
        OperationHandler additionHandler = new AdditionHandler(StorageTest.storage);
        additionHandler.apply(new FruitDto("s", "banana", 100));
        int expected = 200;
        int actual = StorageTest.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);

        additionHandler.apply(new FruitDto("r", "banana", 100));
        expected = 300;
        actual = StorageTest.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_constructor_notOk() {
        OperationHandler purchaseHandler = new AdditionHandler(null);
    }
}
