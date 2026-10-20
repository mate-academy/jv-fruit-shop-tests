package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final SupplyOperation supplyOperation = new SupplyOperation();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_zeroQuantity_ok() {
        Storage.fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(0);

        supplyOperation.process(transaction);

        assertEquals(50, Storage.fruits.get("banana"));
    }

    // SupplyOperationTest — додай:
    @Test
    void process_multipleSupplies_ok() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setFruit("banana");
        transaction1.setQuantity(50);
        supplyOperation.process(transaction1);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setFruit("banana");
        transaction2.setQuantity(30);
        supplyOperation.process(transaction2);

        assertEquals(80, Storage.fruits.get("banana"));
    }

    @Test
    void process_addToExisting_ok() {
        Storage.fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(100);

        supplyOperation.process(transaction);

        assertEquals(150, Storage.fruits.get("banana"));
    }

    @Test
    void process_newFruit_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(50);

        supplyOperation.process(transaction);

        assertEquals(50, Storage.fruits.get("apple"));
    }
}
