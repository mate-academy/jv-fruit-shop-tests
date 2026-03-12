package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        storage.addFruitWithQuantity("banana", 20);
        supplyOperation = new SupplyOperation(storage);
    }

    @Test
    public void handle_fruitNotFoundInStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(200);
        supplyOperation.handle(fruitTransaction);
        assertEquals(200, storage.findQuantity("apple"));
    }

    @Test
    public void handle_fruitFoundInStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(200);
        supplyOperation.handle(fruitTransaction);
        assertEquals(220, storage.findQuantity("banana"));
    }
}
