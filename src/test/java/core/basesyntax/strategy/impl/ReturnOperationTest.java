package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        operationHandler = new ReturnOperation();
    }

    @Test
    void apply_existingFruit_addsReturnedQuantity() {
        Storage.getStorage().put("apple", 80);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10);

        operationHandler.apply(transaction);

        assertEquals(90, Storage.getStorage().get("apple"));
    }
}
