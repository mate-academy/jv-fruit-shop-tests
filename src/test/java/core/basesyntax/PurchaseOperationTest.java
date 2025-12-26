package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.PurchaseOperation;

class PurchaseOperationTest {
    private final PurchaseOperation operation = new PurchaseOperation();

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handle_PurchaseOk() {
        Storage.fruitStorage.put("apple", 20);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple", 5);

        operation.handle(transaction);

        int actual = Storage.fruitStorage.get("apple");
        assertEquals(15, actual);
    }

    @Test
    void handle_PurchaseMoreThanExists_ThrowsException() {
        Storage.fruitStorage.put("apple", 10);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20);

        assertThrows(RuntimeException.class, () -> {
            operation.handle(transaction);
        });
    }
}
