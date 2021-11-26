package core.basesyntax.strategy;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.TransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.PurchaseOperation;
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
    public void apply_correctWorkOperation_ok() {
        Fruit fruit = new Fruit("apple");
        Storage.storage.put(fruit, 100);
        TransactionDto transaction = new TransactionDto("p", "apple", 100);
        handler.apply(transaction);
        boolean actual = Storage.storage.get(fruit) == 0;
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseMuchThanHave_notOk() {
        TransactionDto transaction = new TransactionDto("p", "apple", 150);
        handler.apply(transaction);
    }
}
