package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void supplyOperation_isOk() {
        supplyOperation = new SupplyOperation();
        Storage.clear();
    }

    @Test
    void setSupplyOperation() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 100);
        supplyOperation.apply(fruitTransaction);
        assertEquals(100, Storage.getQuantity("apple"));
    }

}
