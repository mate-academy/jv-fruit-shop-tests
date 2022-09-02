package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeAll
    public static void init() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    void validPurchaseTransaction_Ok() {
        Fruit fruit = new Fruit();
        fruit.setName("banana");
        Storage.getStorage().put(fruit, 100);
        Transaction transaction = new Transaction("p", fruit, 50);
        purchaseOperationHandler.apply(transaction);
        int expected = 50;
        int actual = Storage.getStorage().get(fruit);
        assertEquals(expected, actual);
    }
}
