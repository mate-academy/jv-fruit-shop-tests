package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.impl.QuantityCalculatorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityCalculatorServiceImplTest {
    private QuantityCalculatorServiceImpl quantityCalculatorService;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    public void setUp() {
        OperationHandlerStrategy operationHandlerStrategy = new TestOperationHandlerStrategy();
        quantityCalculatorService = new QuantityCalculatorServiceImpl(operationHandlerStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @Test
    void calculate_EmptyTransactionList_notOk() {
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }

    @Test
    void calculate_SingleTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 5);
        fruitTransactions.add(fruitTransaction);
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }

    @Test
    void calculate_MultipleTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(Operation.BALANCE, "apple", 5);
        FruitTransaction transaction2 = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        fruitTransactions.add(transaction1);
        fruitTransactions.add(transaction2);
        assertDoesNotThrow(() -> quantityCalculatorService.calculate(fruitTransactions));
    }

    private static class TestOperationHandlerStrategy implements OperationHandlerStrategy {
        public OperationHandler getOperationService(Operation operation) {
            return new TestOperationHandler();
        }
    }

    private static class TestOperationHandler implements OperationHandler {
        public void handle(FruitTransaction fruitTransaction) {

        }
    }
}
