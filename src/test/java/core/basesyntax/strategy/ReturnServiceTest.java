package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.ReturnService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnServiceTest {
    private static ReturnService returnService;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageImpl();
        returnService = new ReturnService((StorageImpl) storage);
    }

    @Test
    void returnService_negativeAmount_notOk() {
        Storage.storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple",-30);
        Assertions.assertThrows(RuntimeException.class,
                () -> returnService.process(fruitTransaction));
    }

    @Test
    void returnService_positiveAmount_Ok() {
        Storage.storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple",30);
        returnService.process(fruitTransaction);
        int actual = Storage.storage.get("apple");
        Assertions.assertEquals(50, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
