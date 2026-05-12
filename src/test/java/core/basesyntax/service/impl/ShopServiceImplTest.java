package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private CountingOperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new CountingOperationHandler();
        OperationStrategy operationStrategy = operation -> operationHandler;
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void processValidTransactionsAppliesHandlers() {
        FruitTransaction firstTransaction = createTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        FruitTransaction secondTransaction = createTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 15);

        shopService.process(List.of(firstTransaction, secondTransaction));

        assertEquals(2, operationHandler.calls);
        assertEquals("apple", operationHandler.lastFruit);
        assertEquals(15, operationHandler.lastQuantity);
    }

    private FruitTransaction createTransaction(FruitTransaction.Operation operation,
            String fruit, int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }

    private static class CountingOperationHandler implements OperationHandler {
        private int calls;
        private String lastFruit;
        private int lastQuantity;

        @Override
        public void handle(String fruit, int quantity) {
            calls++;
            lastFruit = fruit;
            lastQuantity = quantity;
        }
    }
}
