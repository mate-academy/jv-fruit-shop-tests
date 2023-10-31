package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static OperationService balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    void processBalanceOperation_OK() {
        FruitTransaction appleTransaction = new FruitTransaction(
                Operation.BALANCE, "apple", 120);
        FruitTransaction bananaTransaction = new FruitTransaction(
                Operation.BALANCE, "banana", 100);
        balanceOperation.doOperation(appleTransaction);
        balanceOperation.doOperation(bananaTransaction);
        int appleActual = Storage.getStorage().get("apple");
        assertEquals(120, appleActual);
        int bananaActual = Storage.getStorage().get("banana");
        assertEquals(100, bananaActual);
        balanceOperation.doOperation(appleTransaction);
        int secondAppleActual = Storage.getStorage().get("apple");
        assertEquals(120, secondAppleActual);
    }

    @Test
    void processBalanceOperationWithNegativeValue_notOK() {
        FruitTransaction appleTransaction = new FruitTransaction(
                Operation.BALANCE, "apple", -120);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.doOperation(appleTransaction));
    }

    @Test
    void processBalanceOperationWithValueNull_notOK() {
        FruitTransaction appleTransaction = new FruitTransaction(
                Operation.BALANCE, null, -120);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.doOperation(appleTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
