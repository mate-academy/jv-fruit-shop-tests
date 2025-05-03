package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationImplTest {
    private SupplyOperationImpl supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperationImpl();
    }

    @AfterEach
    void tearDown() {
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

    @Test
    void apply_supplyToNewFruit_addsNewEntry() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 15);
        supplyOperation.apply(transaction);
        assertEquals(15, Storage.getQuantity("banana"));
    }

    @Test
    void apply_zeroQuantity_keepsQuantityUnchanged() {
        Storage.putFruit("orange", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 0);
        supplyOperation.apply(transaction);
        assertEquals(5, Storage.getQuantity("orange"));
    }

    @Test
    void apply_negativeQuantity_reducesStock() {
        Storage.putFruit("grape", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "grape", -5);
        supplyOperation.apply(transaction);
        assertEquals(15, Storage.getQuantity("grape"));
    }
}
