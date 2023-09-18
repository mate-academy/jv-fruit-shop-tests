package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.operations.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static PurchaseOperation processOperation;

    @BeforeAll
    static void beforeAll() {
        processOperation = new PurchaseOperation();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void purchaseOperation_validData_ok() {
        String validName = "banana";
        int validQuantity = 10;
        Operation validOperation = Operation.PURCHASE;
        FruitTransaction validTransaction
                = new FruitTransaction(validName, validOperation, validQuantity);

        FruitStorage.storage.put("banana", 10);
        processOperation.processOperation(validTransaction);
        assertEquals(FruitStorage.storage.get(validName), 0);
    }
}
