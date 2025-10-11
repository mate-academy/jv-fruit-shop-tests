package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {

    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() throws Exception {
        purchaseOperation = new PurchaseOperation();

        var field = Storage.class.getDeclaredField("fruitStorage");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        var map = (java.util.Map<String, Integer>) field.get(null);
        map.clear();
    }

    @Test
    void apply_decreasesQuantityCorrectly() {
        Storage.setQuantity("apple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 4
        );

        purchaseOperation.apply(transaction);

        assertEquals(6, Storage.getQuantity("apple"));
    }

    @Test
    void apply_throwsExceptionWhenResultNegative() {
        Storage.setQuantity("banana", 3);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5
        );

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.apply(transaction));
        assertTrue(exception.getMessage().contains("Negative balance for fruit"));
    }
}
