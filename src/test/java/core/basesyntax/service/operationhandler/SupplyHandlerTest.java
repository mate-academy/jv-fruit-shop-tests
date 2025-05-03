package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private SupplyHandler supplyHandler;
    private Map<String, Integer> fruitsMap;

    @BeforeEach
    void setUp() {
        fruitsMap = new HashMap<>();
        Storage.setFruits(fruitsMap);
        supplyHandler = new SupplyHandler();
    }

    @Test
    void successfulFruitReturn() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 5);
        supplyHandler.handleOperation(transaction);
        assertEquals(15, Storage.getFruits().get("apple"));
    }

    @Test
    void returnFruitThatWasNotInStorage() {
        fruitsMap.put("apple", 0);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 7);
        supplyHandler.handleOperation(transaction);
        assertEquals(7, Storage.getFruits().get("apple"));
    }

    @Test
    void returnZeroThatWasNotInStorage() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 0);
        supplyHandler.handleOperation(transaction);
        assertEquals(10, Storage.getFruits().get("apple"));
    }
}
