package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitDao fruitDao = new FruitDaoImpl();
    private OperationStrategy balanceOperation = new BalanceOperation(fruitDao);

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void balanceOperation_addNewFruit_isOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 40);
        balanceOperation.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 40;
        Assertions.assertEquals(expected, actual);
    }
}
