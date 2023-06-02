package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.PurchaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseServiceTest {
    private static PurchaseService purchaseService;
    private static StorageImpl storageImpl;

    @BeforeAll
    static void beforeAll() {
        storageImpl = new StorageImpl();
        purchaseService = new PurchaseService(storageImpl);
    }

    @Test
    void purchaseService_negativeAmount_notOk() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple",-30);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseService.process(fruitTransaction));
    }

    @Test
    void purchaseService_quantityMoreAmount_Ok() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple",30);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseService.process(fruitTransaction));
    }

    @Test
    void purchaseService_positiveAmount_Ok() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple",10);
        purchaseService.process(fruitTransaction);
        int actual = storage.get("apple");
        Assertions.assertEquals(10, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
