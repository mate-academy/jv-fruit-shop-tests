package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        Storage.clear();
        returnOperation = new ReturnOperation();
    }

    @Test
    public void shouldIncreaseQuantityWhenReturn() {
        returnOperation.apply(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 5));
        returnOperation.apply(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 5));
        int actual = Storage.getQuantity("banana");
        assertEquals(10, actual);
    }
}
