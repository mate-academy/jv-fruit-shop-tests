package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ReturnHandler returnHandler;
    private Map<String, Integer> fruitsMap;

    @BeforeEach
    void setUp() {
        fruitsMap = new HashMap<>();
        Storage.setFruits(fruitsMap);
        returnHandler = new ReturnHandler();
    }

    @Test
    void successfulFruitReturn() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 5);
        returnHandler.handleOperation(transaction);
        assertEquals(15, fruitsMap.get("apple"));
    }

    @Test
    void returnFruitThatWasNotInStorage() {
        fruitsMap.put("apple", 0);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 7);
        returnHandler.handleOperation(transaction);
        assertEquals(7, fruitsMap.get("apple"));
    }

    @Test
    void returnZeroThatWasNotInStorage() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 0);
        returnHandler.handleOperation(transaction);
        assertEquals(10, fruitsMap.get("apple"));
    }
}
