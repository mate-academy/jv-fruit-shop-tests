package core.basesyntax.servicehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {

    private Map<String, Integer> fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new HashMap<>();
    }

    @Test
    public void testPurchaseHandler_Success() {
        fruitStorage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5);
        PurchaseHandler handler = new PurchaseHandler(fruitStorage);
        handler.handle(transaction);

        assertEquals(5, fruitStorage.get("apple"));
    }

    @Test
    public void testPurchaseHandler_InsufficientStock() {
        fruitStorage.put("apple", 3);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5);
        PurchaseHandler handler = new PurchaseHandler(fruitStorage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(transaction);
        });
        assertEquals("Brak wystarczającej ilości owoców: apple. "
                + "Dostępna ilość: 3, żądana ilość: 5", exception.getMessage());
    }

    @Test
    public void testPurchaseHandler_NonExistentFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5);
        PurchaseHandler handler = new PurchaseHandler(fruitStorage);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(transaction);
        });
        assertEquals("Brak wystarczającej ilości owoców: apple. Dostępna ilość: 0,"
                + " żądana ilość: 5", exception.getMessage());
    }
}
