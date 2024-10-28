package core.basesyntax.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        FruitStorage.getFruits().clear();
    }

    @Test
    void createNewTransaction_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "apple", 50);
        balanceOperation.processOperation(fruitTransaction);
        int expectedQuantity = 50;
        int actualQuantity = FruitStorage.getFruits().get("apple");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void transaction_zeroQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "apple", 0);
        balanceOperation.processOperation(fruitTransaction);
        int expectedQuantity = 0;
        int actualQuantity = FruitStorage.getFruits().get("apple");
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void transaction_nullFruitTransaction_notOk() {
        FruitTransaction fruitTransaction = null;
        Assertions.assertThrows(NullPointerException.class, () ->
                balanceOperation.processOperation(fruitTransaction));
    }
}
