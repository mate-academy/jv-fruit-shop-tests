package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Apple", 10);
        Storage.quantities.put("Apple", 10);
        balanceOperation.handle(transaction);
        assertEquals(10, Storage.quantities.get("Apple"));
    }

    @Test
    public void handle_existingFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Banana", 5);
        Storage.quantities.put("Banana", 5);
        balanceOperation.handle(transaction);
        assertEquals(5, Storage.quantities.get("Banana"));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Orange", 7);
        Storage.quantities.put("Orange",7);
        balanceOperation.handle(transaction);
        assertEquals(7, Storage.quantities.get("Orange"));
    }

    @Test
    public void handle_zeroQuantity() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "Grapes", 0);
        Storage.quantities.put("Grapes", 0);
        balanceOperation.handle(transaction);
        assertEquals(0, Storage.quantities.get("Grapes"));
    }
}
