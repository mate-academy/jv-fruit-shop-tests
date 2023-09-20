package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static Operation supplyOperation;

    @BeforeAll
    static void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    public void performOperation_validTransaction_ok() {
        Storage.getFruits().put("banana", 50);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OperationType.RETURN, "banana", 50);
        supplyOperation.performOperation(fruitTransaction);
        assertEquals(100, Storage.getFruits().get("banana"));
    }
}
