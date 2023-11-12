package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private StorageDao storageDao;
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
        storageDao = new FruitDao();
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void handle_expectedTransaction_ok() {
        String fruit = "banana";
        int initialQuantity = 35;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int purchaseQuantity = 20;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(purchaseQuantity);
        purchaseOperationHandler.handle(fruitTransaction);
        int expectedNewBalance = initialQuantity - purchaseQuantity;
        assertEquals(expectedNewBalance, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_purchaseAllFruits_ok() {
        String fruit = "banana";
        int initialQuantity = 40;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int purchaseQuantity = 40;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(purchaseQuantity);
        purchaseOperationHandler.handle(fruitTransaction);
        int expectedNewBalance = initialQuantity - purchaseQuantity;
        assertEquals(expectedNewBalance, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_zeroPurchaseQuantity_ok() {
        String fruit = "apple";
        int initialQuantity = 15;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int purchaseQuantity = 0;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(purchaseQuantity);
        purchaseOperationHandler.handle(fruitTransaction);
        assertEquals(initialQuantity, FruitStorage.fruitStorage.get(fruit));
    }

    @Test
    void handle_nullTransaction_notOk() {
        assertThrows(NullPointerException.class, () -> purchaseOperationHandler.handle(null));
    }

    @Test
    void handle_nullFruit_notOk() {
        int quantity = 25;
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(quantity);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }

    @Test
    void handle_negativeBalance_notOk() {
        String fruit = "banana";
        int initialQuantity = 5;
        FruitStorage.fruitStorage.put(fruit, initialQuantity);
        int purchaseQuantity = 10;
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(purchaseQuantity);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }
}
