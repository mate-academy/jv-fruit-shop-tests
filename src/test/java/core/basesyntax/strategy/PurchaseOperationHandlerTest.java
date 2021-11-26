package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final OperationHandler purchaseHandler = new PurchaseOperationHandler();
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
        TransactionDto transaction1 = new TransactionDto("b", "banana", 50);
        balanceHandler.apply(transaction1);
        TransactionDto transaction2 = new TransactionDto("p", "banana", 45);
        purchaseHandler.apply(transaction2);
        Integer expected = 5;
        Fruit fruit = new Fruit(transaction1.getFruit());
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
    
    @Test(expected = RuntimeException.class)
    public void apply_notEnoughFruits_notOk() {
        TransactionDto transaction1 = new TransactionDto("b", "banana", 50);
        balanceHandler.apply(transaction1);
        TransactionDto transaction2 = new TransactionDto("p", "banana", 60);
        purchaseHandler.apply(transaction2);
    }
    
    @Test(expected = RuntimeException.class)
    public void apply_fruitDoesntExist_notOk() {
        TransactionDto transaction = new TransactionDto("p", "banana", 60);
        purchaseHandler.apply(transaction);
    }
}
