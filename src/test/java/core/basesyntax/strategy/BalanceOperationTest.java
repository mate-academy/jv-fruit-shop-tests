package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private final OperationService balanceOperation = new BalanceOperation();

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

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
