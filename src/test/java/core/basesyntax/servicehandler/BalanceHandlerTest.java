package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testBalanceHandler() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);
        BalanceHandler handler = new BalanceHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(10, fruitStorage.get("apple"));
    }

    @Test
    public void testBalanceHandler_UpdateExistingFruit() {
        fruitStorage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);
        BalanceHandler handler = new BalanceHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(10, fruitStorage.get("apple"));
    }
}
