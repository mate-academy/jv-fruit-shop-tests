package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.StorageFruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.impl.BalanceHandlerImpl;
import core.basesyntax.operation.impl.PurchaseHandlerImpl;
import core.basesyntax.operation.impl.ReturnHandlerImpl;
import core.basesyntax.operation.impl.SuppliersHandlerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static FruitTransaction fruitTransaction;
    private static ShopServiceImpl service;
    private static OperationStrategy operationStrategy;
    
    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategyImpl(Map.of(
                Operation.BALANCE, new BalanceHandlerImpl(),
                Operation.PURCHASE, new PurchaseHandlerImpl(),
                Operation.RETURN, new ReturnHandlerImpl(),
                Operation.SUPPLY, new SuppliersHandlerImpl()));
        service = new ShopServiceImpl(operationStrategy);
    }
    
    @AfterEach
    void tearDown() {
        StorageFruit.storage.clear();
    }
    
    @Test
    void transactionsIsNull_ok() {
        assertThrows(RuntimeException.class, () -> service.process(null));
    }
    
    @Test
    void transactionsIsEmpty_ok() {
        assertDoesNotThrow(() -> service.process(List.of()));
    }
    
    @Test
    void processTransactions_ok() {
        StorageFruit.storage.put("banana", 40);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "banana", 20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransaction);
        service.process(transactions);
        
        assertEquals(StorageFruit.storage.get("banana"), 20);
    }
}
