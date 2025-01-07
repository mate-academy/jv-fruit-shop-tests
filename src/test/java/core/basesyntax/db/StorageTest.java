package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class StorageTest {
    @Test
    void getOperationBalance_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.findOperation("b"));
    }

    @Test
    void getOperationSupply_Ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.findOperation("s"));
    }

    @Test
    void getOperationPurchase_Ok() {
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.findOperation("p"));
    }

    @Test
    void getOperationReturn_Ok() {
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.findOperation("r"));
    }
}
