package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.StorageFruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SuppliersHandlerImplTest {
    @AfterEach
    void tearDown() {
        StorageFruit.storage.clear();
    }
    
    @Test
    void updateNumberOfFruit_existingFruit_ok() {
        StorageFruit.storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 5);
        OperationHandler handler = new SuppliersHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(15, StorageFruit.storage.get("banana"));
    }
    
    @Test
    void updateNumberOfFruit_newFruit_ok() {
        StorageFruit.storage.clear();
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "kiwi", 7);
        OperationHandler handler = new SuppliersHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(7, StorageFruit.storage.get("kiwi"));
    }
    
    @Test
    void updateNumberOfFruit_zeroQuantity_ok() {
        StorageFruit.storage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 0);
        OperationHandler handler = new SuppliersHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(5, StorageFruit.storage.get("apple")); // кількість не зміниться
    }
}
