package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReduceOperationTest {
    private static FruitOperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new ReduceOperation();
    }

    @Test
    public void apply_reduceFruitsFromStorage_isOk() {
        Storage.fruitsContainer.put("banana", 100);
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.PURCHASE, "banana", 75);
        handler.apply(fruitRecord);
        int actual = Storage.fruitsContainer.get("banana");
        assertEquals(25, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_reduceNonExistingFruits_notOk() {
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.PURCHASE, "apple", 50);
        handler.apply(fruitRecord);
    }

    @Test(expected = RuntimeException.class)
    public void apply_reduceFruitsWithLowQuantity_notOk() {
        Storage.fruitsContainer.put("apple", 10);
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.PURCHASE, "apple", 50);
        handler.apply(fruitRecord);
    }

    @After
    public void afterEachTest() {
        Storage.fruitsContainer.clear();
    }
}
