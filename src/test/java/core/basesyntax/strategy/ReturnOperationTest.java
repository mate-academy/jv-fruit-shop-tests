package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.operations.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static ReturnOperation returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void returnOperation_validData_ok() {
        String validName = "banana";
        int validQuantity = 10;
        Operation validOperation = Operation.RETURN;
        FruitTransaction validTransaction
                = new FruitTransaction(validName, validOperation, validQuantity);

        FruitStorage.storage.put("banana", 0);
        returnOperation.processOperation(validTransaction);
        assertEquals(FruitStorage.storage.get(validName), validQuantity);
    }
}
