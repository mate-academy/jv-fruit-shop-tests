package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationImplTest {
    private ReturnOperationImpl returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperationImpl();
        Storage.clearStorage();
    }

    @Test
    void apply_validTransaction_increasesQuantity() {
        Storage.putFruit("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 5);
        returnOperation.apply(transaction);
        assertEquals(15, Storage.getQuantity("banana"));
    }
}
