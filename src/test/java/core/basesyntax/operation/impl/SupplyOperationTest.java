package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation operation;

    @BeforeEach
    void setUp() {
        operation = new SupplyOperation();
    }

    @Test
    void handle_ExistentFruit_Ok() {
        FruitStorage.getStorage().put("watermelon", 1);
        operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "watermelon", 10));
        assertEquals(11, FruitStorage.getStorage().get("watermelon"));
    }

    @Test
    void handle_NotExistentFruit_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "phantom_banana", 10);
        operation.handle(transaction);
        assertEquals(10, FruitStorage.getStorage().get("phantom_banana"));
    }

    @AfterAll
    static void cleanMeth() {
        FruitStorage.getStorage().clear();
    }
}
