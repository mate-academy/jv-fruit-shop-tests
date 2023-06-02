package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.SupplyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyServiceTest {
    private static SupplyService supplyService;
    private static StorageImpl storageImpl;

    @BeforeAll
    static void beforeAll() {
        storageImpl = new StorageImpl();
        supplyService = new SupplyService(storageImpl);
    }

    @Test
    void supplyService_negativeAmount_notOk() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple",-30);
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyService.process(fruitTransaction));
    }

    @Test
    void supplyService_positiveAmount_Ok() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple",30);
        supplyService.process(fruitTransaction);
        int actual = storage.get("apple");
        Assertions.assertEquals(50, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
