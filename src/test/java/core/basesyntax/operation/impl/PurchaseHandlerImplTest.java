package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.StorageFruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerImplTest {
    @AfterEach
    void tearDown() {
        StorageFruit.storage.clear();
    }
    
    @Test
    void updateNumberOfFruit_validPurchase_ok() {
        StorageFruit.storage.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 20);
        OperationHandler handler = new PurchaseHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(30, StorageFruit.storage.get("banana"));
    }
    
    @Test
    void updateNumberOfFruit_exactPurchase_ok() {
        StorageFruit.storage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        OperationHandler handler = new PurchaseHandlerImpl();
        
        handler.updateNumberOfFruit(transaction);
        
        assertEquals(0, StorageFruit.storage.get("apple"));
    }
    
    @Test
    void updateNumberOfFruit_insufficientStock_notOK() {
        StorageFruit.storage.put("kiwi", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "kiwi", 10);
        OperationHandler handler = new PurchaseHandlerImpl();
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                handler.updateNumberOfFruit(transaction)
        );
        assertTrue(ex.getMessage().contains("Not enough kiwi in stock"));
    }
    
    @Test
    void updateNumberOfFruit_noSuchFruit_notOK() {
        StorageFruit.storage.clear(); // жодного фрукту в сховищі
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "mango", 1);
        OperationHandler handler = new PurchaseHandlerImpl();
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                handler.updateNumberOfFruit(transaction)
        );
        assertTrue(ex.getMessage().contains("Can't find any mango"));
    }
    
}
