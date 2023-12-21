package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationBalancePerformerTest {
    private static final int FRUITS_QUANTITY = 20;
    private static OperationPerformer operationPerformer;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    public static void setUp() {
        operationPerformer = new OperationBalancePerformer();
        fruitTransaction = new FruitTransaction("b", "banana", FRUITS_QUANTITY);

    }

    @BeforeEach
    public void initEach() {
        Storage.getFruits().clear();
    }

    @Test
    void perform_allOk() {
        assertDoesNotThrow(() -> operationPerformer.perform(fruitTransaction));

        assertEquals(FRUITS_QUANTITY, Storage.getFruits().get("banana"));
    }
}
