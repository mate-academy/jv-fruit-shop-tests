package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.PurchaseOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String BANANA = "banana";
    private static final int INITIAL_QUANTITY = 20;
    private static final int PURCHASE_QUANTITY = 5;
    private static final int LOW_STOCK = 5;
    private static final int EXCESS_PURCHASE = 10;

    private PurchaseOperation operation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        operation = new PurchaseOperation();
        storage = new HashMap<>();
    }

    @Test
    void process_enoughFruitInStorage_shouldDecreaseStock() {
        storage.put(BANANA, INITIAL_QUANTITY);
        FruitTransaction transaction = createTransaction(BANANA, PURCHASE_QUANTITY);

        operation.process(transaction, storage);

        assertEquals(15, storage.get(BANANA));
    }

    @Test
    void process_notEnoughFruitInStorage_shouldThrowException() {
        storage.put(BANANA, LOW_STOCK);
        FruitTransaction transaction = createTransaction(BANANA, EXCESS_PURCHASE);

        assertThrows(RuntimeException.class,
                () -> operation.process(transaction, storage));
    }

    @Test
    void process_fruitAbsentInStorage_shouldThrowException() {
        FruitTransaction transaction = createTransaction(BANANA, PURCHASE_QUANTITY);

        assertThrows(RuntimeException.class,
                () -> operation.process(transaction, storage));
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
