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
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageImpl();
        supplyService = new SupplyService((StorageImpl) storage);
    }

    @Test
    void supplyService_negativeAmount_notOk() {
        Storage.storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple",-30);
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyService.process(fruitTransaction));
    }

    @Test
    void supplyService_positiveAmount_Ok() {
        Storage.storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple",30);
        supplyService.process(fruitTransaction);
        int actual = Storage.storage.get("apple");
        Assertions.assertEquals(50, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
