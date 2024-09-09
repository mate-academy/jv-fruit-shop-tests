package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        returnOperation = new ReturnOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "Apple", 5);
        Storage.quantities.put("Apple", 15);
        returnOperation.handle(transaction);
        assertEquals(15, Storage.quantities.get("Apple"));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "Orange", 3);
        Storage.quantities.put("Orange", 3);
        returnOperation.handle(transaction);
        assertEquals(3, Storage.quantities.get("Orange"));
    }

    @Test
    public void handle_quantityBecomesZero() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "Banana", 5);
        Storage.quantities.put("Banana", 0);
        returnOperation.handle(transaction);
        assertEquals(0, Storage.quantities.get("Banana"));
    }

    @Test
    public void handle_quantityBecomesPositive() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "Grapes", 10);
        Storage.quantities.put("Grapes", 5);
        returnOperation.handle(transaction);
        assertEquals(5, Storage.quantities.get("Grapes"));
    }
}
