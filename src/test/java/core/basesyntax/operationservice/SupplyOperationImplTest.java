package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationImplTest {
    private SupplyOperationImpl supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperationImpl();
        Storage.clearStorage();
    }

    @Test
    void apply_validTransaction_increasesQuantity() {
        Storage.putFruit("apple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        supplyOperation.apply(transaction);
        assertEquals(30, Storage.getQuantity("apple"));
    }
}
