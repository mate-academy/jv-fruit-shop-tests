package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.Strategy;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static Strategy strategy;
    private static FruitService fruitService;
    private static Storage storage;

    @BeforeAll
    static void setUp() {
        storage = new StorageImpl();
        strategy = new Strategy((StorageImpl) storage);
        fruitService = new FruitServiceImpl(strategy);
    }

    @Test
    void process_withNullInitializeList_notOk() {
        assertThrows(NullPointerException.class, () -> fruitService.process(null));
    }

    @Test
    void process_validData_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(BALANCE, "apple", 100),
                new FruitTransaction(SUPPLY, "apple", 200),
                new FruitTransaction(PURCHASE, "apple", 150),
                new FruitTransaction(RETURN, "apple", 60)
        );
        fruitService.process(fruitTransactionList);
        Integer actual = Storage.storage.get("apple");
        Integer expected = 210;
        assertEquals(expected, actual);
    }
}
