package core.basesyntax.service.oparation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.model.TypeOperations;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitRecordDto fruitRecordDto;

    @Before
    public void setUp() throws Exception {
        Storage.getFruitStorageMap().put(new Fruit("banana"), 0);
        Storage.getFruitStorageMap().put(new Fruit("apple"), 0);
        operationHandler = new SupplyHandler();
        fruitRecordDto = new FruitRecordDto(TypeOperations.BALANCE, "banana", 11);
        operationHandler.apply(fruitRecordDto);
        fruitRecordDto = new FruitRecordDto(TypeOperations.BALANCE, "apple", 3);
        operationHandler.apply(fruitRecordDto);
    }

    @Test
    public void addOperationHandlerTest_Ok() {
        int actual = Storage.getFruitStorageMap().get(new Fruit("banana"));
        int expected = 11;
        assertEquals(actual, expected);
    }

    @Test
    public void addOperationHandlerTest_notOk() {
        int actual = Storage.getFruitStorageMap().get(new Fruit("apple"));
        int expected = 33;
        assertNotEquals(actual, expected);
    }
}
