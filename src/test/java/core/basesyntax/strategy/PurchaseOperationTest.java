package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        storage.addFruitWithQuantity("banana", 30);
        storage.addFruitWithQuantity("orange", 80);
        purchaseOperation = new PurchaseOperation(storage);
    }

    @Test
    public void handle_fruitNotExists_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.handle(fruitTransaction);
        });
    }

    @Test
    public void handle_wrongQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(200);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.handle(fruitTransaction);
        });
    }

    @Test
    public void handle_correctOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(5);
        purchaseOperation.handle(fruitTransaction);
        assertEquals(25, storage.findQuantity("banana"));
    }
}
