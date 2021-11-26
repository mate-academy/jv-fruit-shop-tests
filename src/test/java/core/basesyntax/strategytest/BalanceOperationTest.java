package core.basesyntax.strategytest;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impls.BalanceOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperation();
    }

    @Before
    public void init() {
        Storage.storage.clear();
    }

    @Test
    public void balanceOperation_correctWorkOperation_ok() {
        Fruit fruit = new Fruit("apple");
        TransactionDto transaction = new TransactionDto("b", "apple", 100);
        handler.apply(transaction);
        boolean actual = Storage.storage.get(fruit) == 100;
        assertTrue(actual);
    }
}
