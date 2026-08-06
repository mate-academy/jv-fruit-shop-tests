package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.clearAll();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void process_enoughFruit_decreasesQuantity() {
        Storage.putFruit("banana", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana", 30);

        purchaseOperation.process(transaction);
        assertEquals(70, Storage.getFruit("banana"));
    }

    @Test
    void process_notEnoughFruit_throwsException() {
        Storage.putFruit("apple", 60);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "apple", 100);

        assertThrows(RuntimeException.class,
                () -> purchaseOperation.process(transaction));
        assertEquals(60, Storage.getFruit("apple"));
    }

    @Test
    void process_purchaseAllStock_setsZero() {
        Storage.putFruit("ananas", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "ananas", 100);

        purchaseOperation.process(transaction);
        assertEquals(0, Storage.getFruit("ananas"));
    }
}
