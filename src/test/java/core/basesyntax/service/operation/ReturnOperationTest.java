package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final ReturnOperation returnOperation = new ReturnOperation();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_returnToEmptyStorage_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);

        returnOperation.process(transaction);

        assertEquals(10, Storage.fruits.get("banana"));
    }

    @Test
    void process_validReturn_ok() {
        Storage.fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);

        returnOperation.process(transaction);

        assertEquals(60, Storage.fruits.get("banana"));
    }

    @Test
    void process_returnNewFruit_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        returnOperation.process(transaction);

        assertEquals(10, Storage.fruits.get("apple"));
    }
}
