package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.operations.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void balanceOperation_validData_ok() {
        String validName = "banana";
        int validQuantity = 10;
        Operation validOperation = Operation.BALANCE;
        FruitTransaction validTransaction
                = new FruitTransaction(validName, validOperation, validQuantity);

        balanceOperation.processOperation(validTransaction);
        assertEquals(FruitStorage.storage.get(validName), validQuantity);
    }
}
