package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    void apply_ok() {
        Storage.getFruits().put("apple", 10);

        balanceOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                10));

        assertEquals(10, Storage.getFruits().get("banana"));
    }

    @Test
    void apply_notOk() {
        Storage.getFruits().put("apple", 10);

        balanceOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                10));

        assertNotEquals(3, Storage.getFruits().get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }
}
