package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";
    private static final String ORANGE = "Orange";
    private static final String GRAPES = "Grapes";
    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, 10);
        Storage.quantities.put(APPLE, 10);
        balanceOperation.handle(transaction);
        assertEquals(10, Storage.quantities.get(APPLE));
    }

    @Test
    public void handle_existingFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                BANANA, 5);
        Storage.quantities.put(BANANA, 5);
        balanceOperation.handle(transaction);
        assertEquals(5, Storage.quantities.get(BANANA));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                ORANGE, 7);
        Storage.quantities.put(ORANGE,7);
        balanceOperation.handle(transaction);
        assertEquals(7, Storage.quantities.get(ORANGE));
    }

    @Test
    public void handle_zeroQuantity() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                GRAPES, 0);
        Storage.quantities.put(GRAPES, 0);
        balanceOperation.handle(transaction);
        assertEquals(0, Storage.quantities.get(GRAPES));
    }
}
