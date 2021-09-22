package core.basesyntax.service.oparation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.model.TypeOperations;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitRecordDto fruitRecordDto;

    @Before
    public void setUp() {
        Storage.getFruitStorageMap().put(new Fruit("banana"), 10);
        Storage.getFruitStorageMap().put(new Fruit("apple"), 10);
        operationHandler = new PurchaseHandler();
        fruitRecordDto = new FruitRecordDto(TypeOperations.PURCHASE, "banana", 1);
        operationHandler.apply(fruitRecordDto);
        fruitRecordDto = new FruitRecordDto(TypeOperations.PURCHASE, "apple", 3);
        operationHandler.apply(fruitRecordDto);
    }

    @Test
    public void addOperationHandlerTest_Ok() {
        int actual = Storage.getFruitStorageMap().get(new Fruit("banana"));
        int expected = 9;
        assertEquals(actual, expected);
    }

    @Test
    public void addOperationHandlerTest_notOk() {
        int actual = Storage.getFruitStorageMap().get(new Fruit("apple"));
        int expected = 8;
        assertNotEquals(actual, expected);
    }
}
