package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        Storage.putFruit("apple", 30);
    }

    @Test
    void handle_validReturn_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 15);
        returnOperation.handle(transaction);
        assertEquals(45, Storage.getFruitQuantity("apple"));
    }

    @Test
    void handel_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", -5);
        assertThrows(RuntimeException.class, () -> returnOperation.handle(transaction));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
