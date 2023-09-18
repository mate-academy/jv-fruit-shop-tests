package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.operations.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void returnOperation_validData_ok() {
        String validName = "banana";
        int validQuantity = 10;
        Operation validOperation = Operation.SUPPLY;
        FruitTransaction validTransaction
                = new FruitTransaction(validName, validOperation, validQuantity);

        FruitStorage.storage.put("banana", 0);
        supplyOperation.processOperation(validTransaction);
        assertEquals(FruitStorage.storage.get(validName), validQuantity);
    }
}
