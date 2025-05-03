package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler purchaseOperation;
    private static FruitTransaction fruitTransaction;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        storage = new Storage();
    }

    @Test
    void handle_Purchase_Ok() {
        storage.addFruit("banana", 50);
        purchaseOperation.handle(fruitTransaction, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(30, fruits.get("banana"));
    }
}
