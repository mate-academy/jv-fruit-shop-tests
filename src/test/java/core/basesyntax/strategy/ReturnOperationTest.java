package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import strategy.ReturnOperation;

public class ReturnOperationTest {
    private ReturnOperation returnOperation = new ReturnOperation();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void return_Ok() {
        Storage.fruits.put("Banana",10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN,"Banana",5);
        returnOperation.processOperation(fruitTransaction);
        assertEquals(15,Storage.fruits.get("Banana"));
    }

    @Test
    void returnNotExistingFruit() {
        Storage.fruits.put("Apple", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "Banana", 5);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            returnOperation.processOperation(fruitTransaction);
        });

        assertEquals("U can not return fruit that was never bought", exception.getMessage());
        assertEquals(10, Storage.fruits.get("Apple"));
    }
}
