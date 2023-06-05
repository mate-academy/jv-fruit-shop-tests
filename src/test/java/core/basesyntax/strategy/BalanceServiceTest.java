package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceServiceTest {
    private static BalanceService balanceService;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageImpl();
        balanceService = new BalanceService((StorageImpl) storage);
    }

    @Test
    void balanceService_negativeAmount_notOk() {
        Storage.storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",-30);
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceService.process(fruitTransaction));
    }

    @Test
    void balanceService_positiveAmount_Ok() {
        Storage.storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",30);
        balanceService.process(fruitTransaction);
        int actual = Storage.storage.get("apple");
        Assertions.assertEquals(30, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
