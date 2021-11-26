package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final OperationHandler handler = new BalanceOperationHandler();
    
    @Before
    public void setUp() {
        Storage.storage.clear();
    }
    
    @After
    public void tearDown() {
        Storage.storage.clear();
    }
    
    @Test
    public void apply_validTransaction_ok() {
        TransactionDto transaction = new TransactionDto("b", "banana", 50);
        handler.apply(transaction);
        Integer expected = 50;
        Fruit fruit = new Fruit(transaction.getFruit());
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
}
