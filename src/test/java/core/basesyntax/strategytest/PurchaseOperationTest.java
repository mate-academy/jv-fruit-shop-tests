package core.basesyntax.strategytest;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handlerimpls.PurchaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperation();
    }

    @Before
    public void init() {
        Storage.storage.clear();
    }

    @Test
    public void purchaseOperation_correctWorkOperation_ok() {
        Fruit fruit = new Fruit("apple");
        Storage.storage.put(fruit, 100);
        boolean expected = true;
        TransactionDto transaction = new TransactionDto("p", "apple", 100);
        handler.apply(transaction);
        System.out.println(Storage.storage.get(fruit));
        boolean actual = Storage.storage.get(fruit) == 0;
        assertEquals(expected, actual);
    }
}
