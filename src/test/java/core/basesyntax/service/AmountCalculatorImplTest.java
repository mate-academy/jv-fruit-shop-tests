package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.DecreaseOperationHandler;
import core.basesyntax.service.operation.IncreaseOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AmountCalculatorImplTest {
    private static AmountCalculator amountCalculator;

    @BeforeClass
    public static void beforeAll() {
        Map<Operation.Type, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(Operation.Type.BALANCE, new IncreaseOperationHandler());
        operationStrategyMap.put(Operation.Type.PURCHASE, new DecreaseOperationHandler());
        operationStrategyMap.put(Operation.Type.RETURN, new IncreaseOperationHandler());
        operationStrategyMap.put(Operation.Type.SUPPLY, new IncreaseOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        amountCalculator = new AmountCalculatorImpl(operationStrategy);
    }

    @Test
    public void calculateDataForReport_ok() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(Operation.Type.BALANCE, "banana", 15));
        operations.add(new Operation(Operation.Type.BALANCE, "apple", 10));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 10);
        Map<String, Integer> actual = amountCalculator.calculateDataForReport(operations);
        Assert.assertEquals("Data wasn't calculated properly", expected, actual);
        operations.add(new Operation(Operation.Type.PURCHASE, "banana", 5));
        operations.add(new Operation(Operation.Type.PURCHASE, "apple", 5));
        expected.put("banana", 10);
        expected.put("apple", 5);
        actual = amountCalculator.calculateDataForReport(operations);
        Assert.assertEquals("Data wasn't calculated properly", expected, actual);
    }

    @Test
    public void calculateDataForReport_nullOperationType_notOk() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(null, "banana", 15));
        Assert.assertThrows("You should throw an exception for wrong operation type",
                RuntimeException.class, () -> amountCalculator.calculateDataForReport(operations));
    }

    @Test
    public void calculateDataForReport_negativeAfterDecrease_notOk() {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(Operation.Type.BALANCE, "banana", 15));
        operations.add(new Operation(Operation.Type.PURCHASE, "banana", 20));
        Assert.assertThrows("You should throw exception for negative amount after operation",
                RuntimeException.class, () -> amountCalculator.calculateDataForReport(operations));
    }
}
