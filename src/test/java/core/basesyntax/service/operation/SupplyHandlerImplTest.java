package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerImplTest {
    private static OperationHandler operationHandler;
    private Map<String, Integer> expected;
    private FruitRecord fruitRecord;

    @BeforeAll
    static void setUp() {
        operationHandler = new SupplyHandlerImpl();
    }

    @Test
    void checkFruitReturn_Ok() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",0);
        fruitRecord = new FruitRecord("b","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }

    @Test
    void checkFruitPurchase_NotOk() {
        expected = new HashMap<>();
        expected.put("banana",10);
        Storage.fruitMap.put("banana",25);
        fruitRecord = new FruitRecord("b","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertNotEquals(expected, Storage.fruitMap);
    }

    @Test
    void checkTwoFruitPurcahse_Ok() {
        expected = new HashMap<>();
        expected.put("banana",12);
        Storage.fruitMap.put("banana",2);
        fruitRecord = new FruitRecord("b","banana",10);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
        expected.put("banana",22);
        operationHandler.applyOperation(fruitRecord);
        assertEquals(expected, Storage.fruitMap);
    }
}
