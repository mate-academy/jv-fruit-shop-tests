package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopServiceImpl fruitShopService;
    private List<FruitTransaction> processedTransactions;

    @BeforeEach
    void setUp() {
        processedTransactions = new ArrayList<>();
        OperationStrategy operationStrategy = new TestOperationStrategy();
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    void processOfOperations_ValidTransactions_SuccessfullyProcessesTransactions() {
        List<FruitTransaction> transactions = createTransactions();

        fruitShopService.processOfOperations(transactions);

        assertEquals(transactions, processedTransactions);
    }

    private List<FruitTransaction> createTransactions() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(createTransaction("apple", 10, FruitTransaction.Operation.PURCHASE));
        transactions.add(createTransaction("banana", 5, FruitTransaction.Operation.RETURN));
        return transactions;
    }

    private FruitTransaction createTransaction(String fruit, int quantity,
                                               FruitTransaction.Operation operation) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        transaction.setOperation(operation);
        return transaction;
    }

    private class TestOperationStrategy implements OperationStrategy {
        private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

        public TestOperationStrategy() {
            operationHandlers = new HashMap<>();
            operationHandlers.put(FruitTransaction.Operation.PURCHASE, new TestOperationHandler());
            operationHandlers.put(FruitTransaction.Operation.RETURN, new TestOperationHandler());
        }

        @Override
        public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
            return operationHandlers.get(operation);
        }
    }

    private class TestOperationHandler implements OperationHandler {
        @Override
        public void handleTransaction(FruitTransaction fruitTransaction) {
            processedTransactions.add(fruitTransaction);
        }
    }
}
