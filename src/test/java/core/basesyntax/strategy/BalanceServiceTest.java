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
    private static StorageImpl storageImpl;

    @BeforeAll
    static void beforeAll() {
        storageImpl = new StorageImpl();
        balanceService = new BalanceService(storageImpl);
    }

    @Test
    void balanceService_negativeAmount_notOk() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",-30);
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceService.process(fruitTransaction));
    }

    @Test
    void balanceService_positiveAmount_Ok() {
        storage.put("apple", 20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",30);
        balanceService.process(fruitTransaction);
        int actual = storage.get("apple");
        Assertions.assertEquals(30, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }
}
