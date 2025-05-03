package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;
    private Map<String, Integer> fruitsMap;

    @BeforeEach
    void setUp() {
        fruitsMap = new HashMap<>();
        Storage.setFruits(fruitsMap);
        balanceHandler = new BalanceHandler();
    }

    @Test
    void shouldAddFruitToStorage() {
        fruitsMap.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        balanceHandler.handleOperation(transaction);
        assertEquals(10, fruitsMap.get("apple"));
    }
}
