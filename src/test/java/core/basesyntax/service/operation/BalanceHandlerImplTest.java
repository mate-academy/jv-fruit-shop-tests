package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerImplTest {
    private static OperationHandler operationHandler;
    private static Map<String, Integer> expected;
    private FruitRecord fruitRecord;

    @BeforeClass
    public static void setUp() {
        operationHandler = new BalanceHandlerImpl();
        expected = new HashMap<>();
    }

    @Test
    public void checkFruitRecordAdd_Ok() {
        expected.put("banana",10);
        fruitRecord = new FruitRecord("b","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }

    @Test
    public void checkTwoFruitRecordsAdd_Ok() {
        expected.put("banana",7);
        fruitRecord = new FruitRecord("b","banana",7);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
        expected.put("avocado",12);
        operationHandler.applyOperation(new FruitRecord("b", "avocado", 12));
        assertEquals(expected, Storage.fruitMap);
    }

}
