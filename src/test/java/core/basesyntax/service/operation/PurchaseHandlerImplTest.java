package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static OperationHandler operationHandler;
    private Map<String, Integer> expected;
    private FruitRecord fruitRecord;

    @BeforeClass
    public static void setUp() {
        operationHandler = new PurchaseHandlerImpl();
    }

    @After
    public void everyTest() {
        Storage.fruitRecordList.clear();
        Storage.fruitMap.clear();
    }

    @Test
    public void checkFruitPurchase_Ok() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",20);
        fruitRecord = new FruitRecord("p","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }

    @Test
    public void checkFruitPurchase_NotOk() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",25);
        fruitRecord = new FruitRecord("p","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertNotEquals(expected, Storage.fruitMap);
    }

    @Test
    public void checkTwoFruitPurcahse_Ok() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",20);
        fruitRecord = new FruitRecord("p","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
        expected.put("banana",0);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }

}
