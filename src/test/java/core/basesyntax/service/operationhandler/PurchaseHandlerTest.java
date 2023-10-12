package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operationhadler.PurchaseHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private Storage fruitStorage;
    private String testFruit = "banana";
    private OperationHandler purchaseHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        fruitStorage = new Storage();
        Storage.fruitsStorage.put("banana", 20);
        purchaseHandler = new PurchaseHandler();
        transaction = new FruitTransaction();
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void removeCorrectPurchaseQuantity_ok() {
        transaction.setFruit(testFruit);
        transaction.setQuantity(7);
        purchaseHandler.handleTransaction(transaction);
        int expectedQuantity = 13;
        int actualQuantity = Storage.fruitsStorage.get(testFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void incorrectPurchaseQuantity_notOk() {
        int quantityMoreThanBalance = 21;
        OperationHandler purchaseHandler = new PurchaseHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(testFruit);
        transaction.setQuantity(quantityMoreThanBalance);
        assertThrows(RuntimeException.class, () -> {
            purchaseHandler.handleTransaction(transaction);
        });
    }
}
