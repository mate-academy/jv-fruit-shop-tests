package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String APPLE = "Apple";
    private static final String KIWI = "Kiwi";
    private static final String GRAPES = "Grapes";
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, 5);
        Storage.quantities.put(APPLE, 5);
        supplyOperation.handle(transaction);
        assertEquals(10, Storage.quantities.get(APPLE));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                KIWI, 3);
        Storage.quantities.put(KIWI, 3);
        supplyOperation.handle(transaction);
        assertEquals(6, Storage.quantities.get(KIWI));
    }

    @Test
    public void handle_quantityBecomesNegative_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                GRAPES, -10);
        Storage.quantities.put(GRAPES, -10);
        supplyOperation.handle(transaction);
        assertEquals(-20, Storage.quantities.get(GRAPES));
    }
}
