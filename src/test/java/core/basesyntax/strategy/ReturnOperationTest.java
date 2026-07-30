package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final ReturnOperation returnOperation = new ReturnOperation();

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void apply_validReturnTransaction_removeFruitsFromStorage() {
        Storage.getFruits().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 50);
        returnOperation.apply(transaction);
        int actualQuantity = Storage.getFruits().get("apple");

        assertEquals(150, actualQuantity,
                "Storage should contain 150 apples after RETURN operation");
    }

    @Test
    void apply_negativeQuantityReturnTransaction_throwRuntimeException() {
        Storage.getFruits().put("banana", 67);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", -69);
        Assertions.assertThrows(RuntimeException.class, () -> {
            returnOperation.apply(transaction);
        }, "Return operation should throw exception if quantity is less than 0");
    }
}
