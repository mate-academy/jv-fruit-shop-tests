package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation operation;

    @BeforeEach
    void setUp() {
        Storage.clearAll();
        operation = new SupplyOperation();
    }

    @Test
    void process_existingFruit_addsQuantity() {
        Storage.putFruit("banana", 100);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50);
        operation.process(transaction);
        assertEquals(150, Storage.getFruit("banana"));
    }

    @Test
    void process_fruitNoInStorage_addsFruitsToStorage() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);

        operation.process(fruitTransaction);
        assertEquals(50, Storage.getFruit("apple"));
    }
}
