package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        storage.addFruitWithQuantity("banana", 30);
        storage.addFruitWithQuantity("apple", 100);
        storage.addFruitWithQuantity("orange", 50);
        returnOperation = new ReturnOperation(storage);
    }

    @Test
    public void handle_fruitNotFoundInStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("mandarin");
        fruitTransaction.setQuantity(10);
        returnOperation.handle(fruitTransaction);
        assertEquals(10, storage.findQuantity("mandarin"));
    }

    @Test
    public void handle_fruitFoundInStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("orange");
        fruitTransaction.setQuantity(10);
        returnOperation.handle(fruitTransaction);
        assertEquals(60, storage.findQuantity("orange"));
    }
}
