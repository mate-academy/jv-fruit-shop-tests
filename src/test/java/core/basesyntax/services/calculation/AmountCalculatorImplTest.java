package core.basesyntax.services.calculation;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.services.operation.DecreaseOperationHandler;
import core.basesyntax.services.operation.IncreaseOperationHandler;
import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.operation.Strategy;
import core.basesyntax.services.operation.StrategyImpl;
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
        Map<TransactionDto.Type, OperationHandler> operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(TransactionDto.Type.BALANCE, new IncreaseOperationHandler());
        operationStrategyMap.put(TransactionDto.Type.PURCHASE, new DecreaseOperationHandler());
        operationStrategyMap.put(TransactionDto.Type.RETURN, new IncreaseOperationHandler());
        operationStrategyMap.put(TransactionDto.Type.SUPPLY, new IncreaseOperationHandler());
        Strategy operationStrategy = new StrategyImpl(operationStrategyMap);
        amountCalculator = new AmountCalculatorImpl(operationStrategy);
    }

    @Test
    public void calculateDataForReport_ok() {
        List<TransactionDto> data = new ArrayList<>();
        data.add(new TransactionDto(TransactionDto.Type.BALANCE, "banana", 30));
        data.add(new TransactionDto(TransactionDto.Type.BALANCE, "apple", 50));
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 30);
        expected.put("apple", 50);
        Map<String, Integer> actual = amountCalculator.calculateDataForReport(data);
        Assert.assertEquals("Data wasn't calculated properly", expected, actual);
        data.add(new TransactionDto(TransactionDto.Type.PURCHASE, "banana", 10));
        data.add(new TransactionDto(TransactionDto.Type.PURCHASE, "apple", 20));
        expected.put("banana", 20);
        expected.put("apple", 30);
        actual = amountCalculator.calculateDataForReport(data);
        Assert.assertEquals("Data wasn't calculated properly", expected, actual);
    }

    @Test
    public void calculateDataForReport_nullOperationType_notOk() {
        List<TransactionDto> data = new ArrayList<>();
        data.add(new TransactionDto(null, "banana", 15));
        Assert.assertThrows("You should throw an exception for wrong operation type",
                RuntimeException.class, () -> amountCalculator.calculateDataForReport(data));
    }
}
