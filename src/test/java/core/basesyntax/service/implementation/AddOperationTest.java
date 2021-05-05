package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static FruitOperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new AddOperation();
    }

    @After
    public void tearDown() {
        Storage.fruitsContainer.clear();
    }

    @Test
    public void apply_correctRecord_isOk() {
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.SUPPLY, "banana", 100);
        handler.apply(fruitRecord);
        int actual = Storage.fruitsContainer.get("banana");
        assertEquals(100, actual);
    }

    @Test
    public void apply_newCorrectRecord_isOk() {
        Storage.fruitsContainer.put("banana", 100);
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.RETURN, "banana", 100);
        handler.apply(fruitRecord);
        int actual = Storage.fruitsContainer.get("banana");
        assertEquals(200, actual);
    }
}
