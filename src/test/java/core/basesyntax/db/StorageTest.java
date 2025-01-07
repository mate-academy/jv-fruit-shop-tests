package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class StorageTest {
    @Test
    void getOperationBalance_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.getOperation("b"));
    }

    @Test
    void getOperationSupply_Ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.getOperation("s"));
    }

    @Test
    void getOperationPurchase_Ok() {
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.getOperation("p"));
    }

    @Test
    void getOperationReturn_Ok() {
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.getOperation("r"));
    }
}
