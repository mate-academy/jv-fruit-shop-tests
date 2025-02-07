package core.basesyntax.strategy.operation;

import core.basesyntax.data.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final ReturnOperation returnOperation = new ReturnOperation();

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validReturnTransaction_increasesStock() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "grape", 20);
        returnOperation.apply(transaction);

        Assertions.assertEquals(20, Storage.getInventory().get("grape"));
    }
}
