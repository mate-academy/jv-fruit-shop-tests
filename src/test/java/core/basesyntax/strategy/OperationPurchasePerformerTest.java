package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationPurchasePerformerTest {
    private static final int PURCHASE_QUANTITY = 20;
    private static OperationPerformer operationPerformer;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    public static void setUp() {
        operationPerformer = new OperationPurchasePerformer();
        fruitTransaction = new FruitTransaction("p", "banana", PURCHASE_QUANTITY);
    }

    @BeforeEach
    public void initEach() {
        Storage.getFruits().clear();
    }

    @Test
    void perform_allOk() {
        Storage.getFruits().put("banana", 30);
        int expectedQuantity = Storage.getFruits().get("banana") - PURCHASE_QUANTITY;

        assertDoesNotThrow(() -> operationPerformer.perform(fruitTransaction));
        assertEquals(expectedQuantity, Storage.getFruits().get("banana"));
    }

    @Test
    void perform_notValidQuantity_notOk() {
        Storage.getFruits().put("banana", 10);

        assertThrows(RuntimeException.class, () -> operationPerformer.perform(fruitTransaction));
    }

}
