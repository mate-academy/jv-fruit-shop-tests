package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationSupplyPerformerTest {
    private static OperationPerformer operationPerformer;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    public static void setUp() {
        operationPerformer = new OperationSupplyPerformer();
    }

    @BeforeEach
    public void initEach() {
        Storage.getFruits().clear();
        Storage.getFruits().put("apple", 20);
    }

    @Test
    void perform_allOk() {
        fruitTransaction = new FruitTransaction("s", "apple", 30);
        int expected = Storage.getFruits().get("apple") + fruitTransaction.getQuantity();
        assertDoesNotThrow(() -> operationPerformer.perform(fruitTransaction));
        assertEquals(expected, Storage.getFruits().get("apple"));

        fruitTransaction = new FruitTransaction("s", "apple", 0);
        assertDoesNotThrow(() -> operationPerformer.perform(fruitTransaction));
        assertEquals(expected, Storage.getFruits().get("apple"));
    }
}
