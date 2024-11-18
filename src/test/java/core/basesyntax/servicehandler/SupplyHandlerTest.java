package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testSupplyHandler_AddFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10);
        SupplyHandler handler = new SupplyHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(10, fruitStorage.get("apple"));
    }

    @Test
    public void testSupplyHandler_AddMoreFruit() {
        fruitStorage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10);
        SupplyHandler handler = new SupplyHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(15, fruitStorage.get("apple"));
    }

    @Test
    public void testSupplyHandler_EmptyStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 7);
        SupplyHandler handler = new SupplyHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(7, fruitStorage.get("banana"));
    }
}
