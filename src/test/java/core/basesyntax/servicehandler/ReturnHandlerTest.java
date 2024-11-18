package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testReturnHandler_AddFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 5);
        ReturnHandler handler = new ReturnHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(5, fruitStorage.get("apple"));
    }

    @Test
    public void testReturnHandler_ReturnMoreFruit() {
        fruitStorage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 5);
        ReturnHandler handler = new ReturnHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(15, fruitStorage.get("apple"));
    }

    @Test
    public void testReturnHandler_NewFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 7);
        ReturnHandler handler = new ReturnHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(7, fruitStorage.get("banana"));
    }
}
