package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Apple", 5);
        Storage.quantities.put("Apple", 5);
        supplyOperation.handle(transaction);
        assertEquals(10, Storage.quantities.get("Apple"));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Kiwi", 3);
        Storage.quantities.put("Kiwi", 3);
        supplyOperation.handle(transaction);
        assertEquals(6, Storage.quantities.get("Kiwi"));
    }

    @Test
    public void handle_quantityBecomesNegative_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Grapes", -10);
        Storage.quantities.put("Grapes", -10);
        supplyOperation.handle(transaction);
        assertEquals(-20, Storage.quantities.get("Grapes"));
    }
}
