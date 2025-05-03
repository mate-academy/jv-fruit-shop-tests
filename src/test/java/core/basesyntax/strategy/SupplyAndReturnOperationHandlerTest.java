package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyAndReturnOperationHandlerTest {
    private final OperationHandler supplyAndReturnHandler = new SupplyAndReturnOperationHandler();
    
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
        TransactionDto transaction1 = new TransactionDto("s", "banana", 80);
        Fruit fruit = new Fruit(transaction1.getFruit());
        Integer quantity = 20;
        Storage.storage.put(fruit, quantity);
        supplyAndReturnHandler.apply(transaction1);
        Integer expected = 100;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
        TransactionDto transaction2 = new TransactionDto("r", "banana", 100);
        supplyAndReturnHandler.apply(transaction2);
        expected = 200;
        actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
}
