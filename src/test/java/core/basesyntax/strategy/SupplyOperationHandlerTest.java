package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void setUp() {
        handler = new SupplyOperationHandler();
    }

    @Test
    public void returnOperation_Ok() {
        Storage.fruitsStorage.put("banana", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 30);
        handler.operate(fruitTransaction);
        int expected = 80;
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }

}
