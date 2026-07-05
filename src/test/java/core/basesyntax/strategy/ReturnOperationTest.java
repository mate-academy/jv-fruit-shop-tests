package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler returnOperation;

    @BeforeEach
    void setUp() {
        Storage.clear();
        returnOperation = new ReturnOperation();
    }

    @Test
    void process_validTransaction_Ok() {
        Storage.put("banana", 100);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "banana", 20);
        returnOperation.process(transaction);
        assertEquals(120, Storage.get("banana"));
    }

    @Test
    void process_newFruit_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "banana", 20);
        returnOperation.process(transaction);
        assertEquals(20, Storage.get("banana"));
    }
}

