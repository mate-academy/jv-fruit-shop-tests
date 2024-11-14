package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static FruitDao fruitDao;
    private static OperationStrategy balanceOperation;

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        balanceOperation = new BalanceOperation(fruitDao);
    }

    @Test
    void balanceOperation_addNewFruit_isOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 40);
        balanceOperation.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 40;
        assertEquals(expected, actual);
    }
}
