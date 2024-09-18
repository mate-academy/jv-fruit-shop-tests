package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";
    private static final String ORANGE = "Orange";
    private static final String GRAPES = "Grapes";
    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        returnOperation = new ReturnOperation();
    }

    @Test
    public void handle() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                APPLE, 5);
        Storage.quantities.put(APPLE, 15);
        returnOperation.handle(transaction);
        assertEquals(15, Storage.quantities.get(APPLE));
    }

    @Test
    public void handle_newFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                ORANGE, 3);
        Storage.quantities.put(ORANGE, 3);
        returnOperation.handle(transaction);
        assertEquals(3, Storage.quantities.get(ORANGE));
    }

    @Test
    public void handle_quantityIsZero() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                BANANA, 5);
        Storage.quantities.put(BANANA, 0);
        returnOperation.handle(transaction);
        assertEquals(0, Storage.quantities.get(BANANA));
    }

    @Test
    public void handle_quantityIsPositive() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                GRAPES, 10);
        Storage.quantities.put(GRAPES, 5);
        returnOperation.handle(transaction);
        assertEquals(5, Storage.quantities.get(GRAPES));
    }
}
