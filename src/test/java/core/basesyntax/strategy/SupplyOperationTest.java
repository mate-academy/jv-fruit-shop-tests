package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        Storage.clear();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void process_validTransaction_Ok() {
        Storage.put("banana", 100);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 20);
        supplyOperation.process(transaction);
        assertEquals(120, Storage.get("banana"));
    }

    @Test
    void process_newFruit_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana", 20);
        supplyOperation.process(transaction);
        assertEquals(20, Storage.get("banana"));
    }
}
