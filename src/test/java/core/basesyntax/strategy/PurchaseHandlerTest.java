package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void init() {
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    void clear() {
        fruitStorage.clear();
    }

    @Test
    void purchaseHandler_processingCorrectData_ok() {
        fruitStorage.put("banana", 100);
        purchaseHandler.handleTransaction(new FruitTransaction("banana", 10,
                FruitTransaction.Operation.PURCHASE));
        assertEquals(fruitStorage.get("banana"), 90);
    }

    @Test
    void purchaseHandler_processingIncorrectTransaction_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("banana", 101,
                        FruitTransaction.Operation.PURCHASE)));
        assertThrows(NoSuchElementException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("apple", 100,
                        FruitTransaction.Operation.PURCHASE)));
        assertThrows(IllegalArgumentException.class,
                () -> purchaseHandler.handleTransaction(new FruitTransaction("banana", -10,
                        FruitTransaction.Operation.PURCHASE)));
    }
}
