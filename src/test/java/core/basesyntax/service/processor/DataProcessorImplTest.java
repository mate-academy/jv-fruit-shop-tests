package core.basesyntax.service.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceHandler;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseHandler;
import core.basesyntax.operations.ReturnHandler;
import core.basesyntax.operations.SupplyHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataProcessorImplTest {
    private static final List<FruitTransaction> EMPTY_FRUIT_TRANSACTION_LIST =
            Collections.emptyList();
    private static List<FruitTransaction> correctFruitTransactionList;
    private static OperationStrategy operationStrategy;
    private static DataProcessor dataProcessor;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(getOperationHandlerMao());
        dataProcessor = new DataProcessorImpl(operationStrategy);
        correctFruitTransactionList = getCorrectFruitTransaction();
    }

    @Test
    void calculateData_isOk() {
        String actualCalculateData = dataProcessor.calculateData(correctFruitTransactionList);
        String expectedCalculateData = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        assertEquals(expectedCalculateData, actualCalculateData);
    }

    @Test
    void calculateDataFromEmptyList_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataProcessor.calculateData(EMPTY_FRUIT_TRANSACTION_LIST);
        });
        String expectedMassage = "Fruit list is empty "
                + EMPTY_FRUIT_TRANSACTION_LIST;
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    private static List<FruitTransaction> getCorrectFruitTransaction() {
        return List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }

    private static Map<FruitTransaction.Operation, OperationHandler> getOperationHandlerMao() {
        return Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler());
    }
}
