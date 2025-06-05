package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation operation;

    @BeforeEach
    void setUp() {
        operation = new ReturnOperation();
    }

    @Test
    void handle_Ok() {
        FruitStorage.getStorage().put("pineapple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "pineapple", 10);
        operation.handle(transaction);
        assertEquals(20, FruitStorage.getStorage().get("pineapple"));
    }

    @Test
    void handle_NotExisting_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "new_banana", 10);
        operation.handle(transaction);
        assertEquals(10, FruitStorage.getStorage().get("new_banana"));
    }

    @AfterAll
    static void cleanMeth() {
        FruitStorage.getStorage().clear();
    }
}
