package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.service.ShopService;
import java.util.List;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    static class TestOperationHandler implements OperationHandler {
        private FruitTransaction receivedTransaction;
        
        @Override
        public void updateNumberOfFruit(FruitTransaction transaction) {
            this.receivedTransaction = transaction;
        }
        
        public FruitTransaction getReceivedTransaction() {
            return receivedTransaction;
        }
    }
    
    static class TestOperationStrategy implements OperationStrategy {
        private final OperationHandler handler;
        
        public TestOperationStrategy(OperationHandler handler) {
            this.handler = handler;
        }
        
        @Override
        public OperationHandler getOperationHandler(Operation operation) {
            return handler;
        }
    }
    
    @Test
    void process_singleTransaction_ok() {
        TestOperationHandler testHandler = new TestOperationHandler();
        OperationStrategy testStrategy = new TestOperationStrategy(testHandler);
        ShopService shopService = new ShopServiceImpl(testStrategy);
        
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 50);
        
        shopService.process(List.of(transaction));
        
        FruitTransaction result = testHandler.getReceivedTransaction();
        assertNotNull(result);
        assertEquals("banana", result.getFruit());
        assertEquals(50, result.getQuantity());
        assertEquals(Operation.BALANCE, result.getOperation());
    }
}
