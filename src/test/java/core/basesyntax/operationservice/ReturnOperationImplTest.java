package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationImplTest {
    private ReturnOperationImpl returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperationImpl();
    }

    @AfterEach
    void tearDown() {
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

    @Test
    void apply_returnToEmptyStorage_addsNewFruit() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 7);
        returnOperation.apply(transaction);
        assertEquals(7, Storage.getQuantity("apple"));
    }

    @Test
    void apply_zeroQuantity_keepsStorageUnchanged() {
        Storage.putFruit("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 0);
        returnOperation.apply(transaction);
        assertEquals(10, Storage.getQuantity("banana"));
    }

    @Test
    void apply_negativeQuantity_allowsReduction() {
        Storage.putFruit("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", -5);
        returnOperation.apply(transaction);
        assertEquals(5, Storage.getQuantity("banana"));
    }
}
