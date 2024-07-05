package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final int expected = 25;
    private OperationHandler purchaseOperationHandler;

    @BeforeEach
     void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.fruitStorage.put("banana", 50);
        Storage.fruitStorage.put("apple",25);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void apply_Purchase_Ok() {
        purchaseOperationHandler.apply(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 25));
        Integer actual = Storage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void apply_PurchaseMoreThanInStock_notOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.apply(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "banana", 60));
        });
    }
}
