package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitOperationHandler;
import core.basesyntax.service.OperationType;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SetOperationTest {
    private static FruitOperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new SetOperation();
    }

    @After
    public void tearDown() {
        Storage.fruitsContainer.clear();
    }

    @Test
    public void apply_addCorrectRecord_isOk() {
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.BALANCE, "apple", 50);
        handler.apply(fruitRecord);
        int actual = Storage.fruitsContainer.get("apple");
        assertEquals(50, actual);
    }

    @Test
    public void apply_setCorrectRecord() {
        FruitRecordDto fruitRecord = new FruitRecordDto(OperationType.BALANCE, "apple", 50);
        FruitRecordDto newFruitRecord = new FruitRecordDto(OperationType.BALANCE, "apple", 150);
        handler.apply(fruitRecord);
        handler.apply(newFruitRecord);
        int actual = Storage.fruitsContainer.get("apple");
        assertEquals(150, actual);
    }
}
