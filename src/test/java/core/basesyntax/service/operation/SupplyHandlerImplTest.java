package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerImplTest {
    private static OperationHandler operationHandler;
    private Map<String, Integer> expected;
    private FruitRecord fruitRecord;

    @BeforeClass
    public static void setUp() {
        operationHandler = new SupplyHandlerImpl();
    }

    @Test
    public void checkFruitSupply_Ok() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",0);
        fruitRecord = new FruitRecord("s","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }

    @Test
    public void checkTwoFruitSupply_Ok() {
        expected = new HashMap<>();
        expected.put("banana",12);
        Storage.fruitMap.put("banana",2);
        fruitRecord = new FruitRecord("s","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
        expected.put("banana",22);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }

    @Test
    public void checkFruitSupply_NotOk() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",25);
        fruitRecord = new FruitRecord("s","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertNotEquals(expected, Storage.fruitMap);
    }
}
