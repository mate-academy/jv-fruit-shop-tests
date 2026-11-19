package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validTransaction_Ok() {
        Storage.add("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 25
        );
        supplyOperation.apply(transaction);
        assertEquals(35, Storage.get("banana"));
    }

    @Test
    void apply_nullTransaction_notOk() {
        assertThrows(Exception.class, () -> supplyOperation.apply(null));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", -10
        );
        assertThrows(IllegalArgumentException.class, () -> supplyOperation.apply(transaction));
    }
}
