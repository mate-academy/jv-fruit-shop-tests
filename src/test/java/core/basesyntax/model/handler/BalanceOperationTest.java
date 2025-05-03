package core.basesyntax.model.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void addFruitToStorage_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 33);
        balanceOperation.handle(fruitTransaction);
        Integer actual = Storage.getStorage().getOrDefault("banana", 33);
        assertEquals(Integer.valueOf(33), actual);
    }

    @Test
    void addFruitZeroQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 0);
        balanceOperation.handle(fruitTransaction);
        Integer actualQuantity = Storage.getStorage().getOrDefault("banana", 0);
        assertEquals(Integer.valueOf(0), actualQuantity);
    }

    @Test
    void addNullTransaction_NotOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                -23);
        assertThrows(IllegalArgumentException.class, () -> balanceOperation.handle(transaction));
    }

    @Test
    void addFruitNegativeQuantity_NotOK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -45);
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.handle(fruitTransaction));
    }
}
