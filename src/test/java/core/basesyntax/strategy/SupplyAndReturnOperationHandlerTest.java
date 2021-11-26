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
    private final OperationHandler balanceHandler = new BalanceOperationHandler();
    
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
        TransactionDto transaction1 = new TransactionDto("b", "banana", 20);
        balanceHandler.apply(transaction1);
        TransactionDto transaction2 = new TransactionDto("s", "banana", 80);
        supplyAndReturnHandler.apply(transaction2);
        Integer expected = 100;
        Fruit fruit = new Fruit(transaction1.getFruit());
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
        TransactionDto transaction3 = new TransactionDto("r", "banana", 100);
        supplyAndReturnHandler.apply(transaction3);
        expected = 200;
        actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
}
