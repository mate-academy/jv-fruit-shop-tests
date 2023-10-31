package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static Operation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    public void performOperation_validTransaction_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(OperationType.BALANCE, "apple", 50);
        balanceOperation.performOperation(fruitTransaction);
        assertEquals(50, Storage.getFruits().get("apple"));
    }
}
