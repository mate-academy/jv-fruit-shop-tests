package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS = List.of(
            createFruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 20),
            createFruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 10)
    );
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> afterProcess;

    @BeforeEach
    void setUp() {
        afterProcess = new ArrayList<>();
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyTest());
    }

    @Test
    void processingOperations_validTransactions_Ok() {
        fruitShopService.processingOperations(FRUIT_TRANSACTIONS);
        assertEquals(FRUIT_TRANSACTIONS, afterProcess);

    }

    private static FruitTransaction createFruitTransaction(FruitTransaction.Operation operation,
                                                    String fruit,
                                                    int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    private static class OperationStrategyTest implements OperationStrategy {
        private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

        public OperationStrategyTest() {
            operationHandlerMap = new HashMap<>();
            operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                    new OperationHandlerTest());
            operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                    new OperationHandlerTest());
        }

        @Override
        public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
            return operationHandlerMap.get(operation);
        }
    }

    private static class OperationHandlerTest implements OperationHandler {
        @Override
        public void handleTransaction(FruitTransaction transaction) {
            afterProcess.add(transaction);
        }
    }
}
