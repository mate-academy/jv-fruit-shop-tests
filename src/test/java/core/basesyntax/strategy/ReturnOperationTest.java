package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static Operation returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    public void performOperation_validTransaction_ok() {
        Storage.getFruits().put("banana", 30);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OperationType.RETURN, "banana", 10);
        returnOperation.performOperation(fruitTransaction);
        assertEquals(40, Storage.getFruits().get("banana"));
    }
}
