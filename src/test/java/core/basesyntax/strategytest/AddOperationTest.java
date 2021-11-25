package core.basesyntax.strategytest;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handlerimpls.AddOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new AddOperation();
    }

    @Before
    public void init() {
        Storage.storage.clear();
    }

    @Test
    public void addOperation_correctWorkOperation_s_ok() {
        boolean expected = true;
        Fruit fruit = new Fruit("apple");
        Storage.storage.put(fruit, 100);
        TransactionDto transaction = new TransactionDto("s", "apple", 100);
        handler.apply(transaction);
        boolean actual = Storage.storage.get(fruit) == 200;
        assertEquals(expected, actual);
    }

    @Test
    public void addOperation_correctWorkOperation_r_ok() {
        boolean expected = true;
        Fruit fruit = new Fruit("apple");
        Storage.storage.put(fruit, 100);
        TransactionDto transaction = new TransactionDto("r", "apple", 100);
        handler.apply(transaction);
        boolean actual = Storage.storage.get(fruit) == 200;
        assertEquals(expected, actual);
    }
}
