package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationHandler returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validTransaction_Ok() {
        Storage.add("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 20
        );
        returnOperation.apply(transaction);
        assertEquals(30, Storage.get("banana"));
    }

    @Test
    void apply_nullTransaction_notOk() {
        assertThrows(Exception.class, () -> returnOperation.apply(null));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", -5
        );
        assertThrows(IllegalArgumentException.class, () -> returnOperation.apply(transaction));
    }
}
