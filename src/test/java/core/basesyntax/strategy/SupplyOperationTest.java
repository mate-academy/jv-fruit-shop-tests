package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import strategy.SupplyOperation;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation = new SupplyOperation();

    @Test
    void supplyWithNegativeAmount() {
        Storage.fruits.put("Banana", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Banana", -5);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyOperation.processOperation(fruitTransaction);
        });

        assertEquals("U can not supply negative amount of fruits", exception.getMessage());
        assertEquals(10, Storage.fruits.get("Banana"));
    }

    @Test
    void supply_Ok() {
        Storage.fruits.put("Banana", 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Banana", 5);
        supplyOperation.processOperation(fruitTransaction);
        assertEquals(15, Storage.fruits.get("Banana"));

    }
}
