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
        Fruit fruit = new Fruit("banana");
        Integer quantity = 50;
        Storage.storage.put(fruit, quantity);
        TransactionDto transaction = new TransactionDto("p", "banana", 45);
        purchaseHandler.apply(transaction);
        Integer expected = 5;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
    
    @Test(expected = RuntimeException.class)
    public void apply_notEnoughFruits_notOk() {
        Fruit fruit = new Fruit("banana");
        Integer quantity = 50;
        Storage.storage.put(fruit, quantity);
        TransactionDto transaction = new TransactionDto("p", "banana", 60);
        purchaseHandler.apply(transaction);
    }
    
    @Test(expected = RuntimeException.class)
    public void apply_fruitDoesntExist_notOk() {
        TransactionDto transaction = new TransactionDto("p", "banana", 60);
        purchaseHandler.apply(transaction);
    }
}
