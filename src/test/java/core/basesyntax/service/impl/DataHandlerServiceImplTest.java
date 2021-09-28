package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.DataHandlerService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.DecreaseOperationHandler;
import core.basesyntax.service.strategy.impl.IncreaseOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataHandlerServiceImplTest {
    private static DataHandlerService dataHandlerService;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitRecord.Operation, OperationHandler> operationsMap = Map.of(
                FruitRecord.Operation.BALANCE, new IncreaseOperationHandler(),
                FruitRecord.Operation.SUPPLY, new IncreaseOperationHandler(),
                FruitRecord.Operation.PURCHASE, new DecreaseOperationHandler(),
                FruitRecord.Operation.RETURN, new IncreaseOperationHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationsMap);
        dataHandlerService = new DataHandlerServiceImpl(operationStrategy);
    }

    @Test
    public void handleData_Ok() {
        List<FruitRecord> operations = new ArrayList<>();
        operations.add(new FruitRecord(FruitRecord.Operation.BALANCE, "banana", 25));
        operations.add(new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 14));
        Map<String, Integer> actual = dataHandlerService.handleData(operations);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 25);
        expected.put("apple", 14);
        String message = "Data calculated incorrectly";
        Assert.assertEquals(message, expected, actual);
        operations.add(new FruitRecord(FruitRecord.Operation.PURCHASE, "banana", 15));
        operations.add(new FruitRecord(FruitRecord.Operation.SUPPLY, "apple", 6));
        expected.put("banana", 10);
        expected.put("apple", 20);
        actual = dataHandlerService.handleData(operations);
        Assert.assertEquals(message, expected, actual);
    }

    @Test
    public void handleDataNegativeValue_NotOk() {
        List<FruitRecord> operations = new ArrayList<>();
        operations.add(new FruitRecord(FruitRecord.Operation.BALANCE, "apple", 8));
        operations.add(new FruitRecord(FruitRecord.Operation.PURCHASE, "apple", 17));
        try {
            dataHandlerService.handleData(operations);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw exception for negative amount");
    }

    @Test
    public void handleDataNullValue_NotOk() {
        List<FruitRecord> operations = new ArrayList<>();
        operations.add(null);
        try {
            dataHandlerService.handleData(operations);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw exception for null value");
    }

    @Test
    public void handleData_NotOk() {
        List<FruitRecord> operations = new ArrayList<>();
        operations.add(new FruitRecord(null, "banana", 200));
        try {
            dataHandlerService.handleData(operations);
        } catch (RuntimeException e) {
            return;
        }
        fail("You should throw an exception for wrong operation");
    }
}
