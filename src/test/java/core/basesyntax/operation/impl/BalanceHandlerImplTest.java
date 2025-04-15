package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.StorageFruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerImplTest {
    private OperationHandler handler = new BalanceHandlerImpl();
    
    @AfterEach
    void cleanUp() {
        StorageFruit.storage.clear();
    }
    
    @Test
    void updateNumberOfFruit_validInput_ok() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", 100);
        
        handler.updateNumberOfFruit(transaction);
        
        assertTrue(StorageFruit.storage.containsKey("banana"));
        assertEquals(100, StorageFruit.storage.get("banana"));
    }
    
    @Test
    void updateNumberOfFruit_overwritesPreviousBalance() {
        StorageFruit.storage.put("banana", 50);
        
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 200);
        OperationHandler handler = new BalanceHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(200, StorageFruit.storage.get("banana"));
    }
    
    @Test
    void updateNumberOfFruit_zeroBalance_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 0);
        OperationHandler handler = new BalanceHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(0, StorageFruit.storage.get("apple"));
    }
}
