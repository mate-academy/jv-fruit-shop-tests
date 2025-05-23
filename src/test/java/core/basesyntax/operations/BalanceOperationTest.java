package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    void getCalculation_addData_Ok() {

        int expectQuantity = 5;
        balanceOperation.getCalculation(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "peach", 5));

        assertTrue(Storage.storage.containsKey("peach")
                        && Storage.storage.get("peach").equals(expectQuantity));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
