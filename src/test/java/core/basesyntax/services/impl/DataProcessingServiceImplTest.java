package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.model.FruitsInStore;
import core.basesyntax.model.OperationType;
import core.basesyntax.services.DataProcessingService;
import core.basesyntax.services.ParametrsValidatorService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperarionHandlerPurchase;
import core.basesyntax.strategy.impl.OperationHandlerBalance;
import core.basesyntax.strategy.impl.OperationHandlerReturn;
import core.basesyntax.strategy.impl.OperationHandlersSupply;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessingServiceImplTest {
    private static DataProcessingService processingService;
    private static List<List<String>> invalidData;
    private static List<List<String>> validData;
    private static Map<String, Integer> expectedData;
    private static List<String> opratorTypeCode;
    private static List<String> fruitsInStore;
    private static ParametrsValidatorService parametrsValidator;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        opratorTypeCode = Arrays.stream(OperationType.values())
                .map(OperationType::getCode)
                .collect(Collectors.toList());
        fruitsInStore = Arrays.stream(FruitsInStore.values())
                .map(FruitsInStore::getCode)
                .collect(Collectors.toList());
        parametrsValidator = new ParametrsValidatorServiseImpl(opratorTypeCode, fruitsInStore);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getCode(), new OperationHandlerBalance());
        operationHandlerMap.put(OperationType.SUPPLY.getCode(), new OperationHandlersSupply());
        operationHandlerMap.put(OperationType.RETURN.getCode(), new OperationHandlerReturn());
        operationHandlerMap.put(OperationType.PURCHASE.getCode(), new OperarionHandlerPurchase());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        validData = List.of(List.of("b", "banana", "20"),
                List.of("b", "apple", "100"),
                List.of("s", "banana", "100"),
                List.of("p", "banana", "13"),
                List.of("r", "apple", "10"),
                List.of("p", "apple", "20"),
                List.of("p", "banana", "5"),
                List.of("s", "banana", "20"));
        expectedData = Map.of(
                FruitsInStore.BANANA.getCode(), 122,
                FruitsInStore.APPLE.getCode(), 90);
    }

    @Before
    public void setUp() {
        invalidData = new ArrayList<>();
        processingService = new DataProcessingServiceImpl(operationStrategy, parametrsValidator);
    }

    @Test
    public void processData_validData_ok() {
        assertEquals(expectedData, processingService.processData(validData));
    }

    @Test(expected = NullDataException.class)
    public void processData_dataNull_notOk() {
        processingService.processData(null);
    }

    @Test(expected = NullDataException.class)
    public void processData_invalidNullData_notOk() {
        invalidData.add(null);
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidData_notOk() {
        invalidData.add(List.of(""));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidMoreParametrs_notOk() {
        invalidData.add(List.of("b", "banana", "100", "we", "w"));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidMoreEmptyParametrs_notOk() {
        invalidData.add(List.of("b", "banana", "100", "", ""));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidEmptyOperation_notOk() {
        invalidData.add(List.of("", "banana", "100"));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidEmptyFruit_notOk() {
        invalidData.add(List.of("b", "", "100"));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidEmptyQuantity_notOk() {
        invalidData.add(List.of("b", "banana", ""));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidOperation_notOk() {
        invalidData.add(List.of("bal", "banana", "100"));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidFruit_notOk() {
        invalidData.add(List.of("b", "tomato", "100"));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidQuantity_notOk() {
        invalidData.add(List.of("b", "banana", "12q"));
        processingService.processData(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void processData_invalidPurchaseQuantity_notOk() {
        invalidData.add(List.of(OperationType.BALANCE.getCode(), "banana", "10"));
        invalidData.add(List.of(OperationType.RETURN.getCode(), "banana", "10"));
        invalidData.add(List.of(OperationType.SUPPLY.getCode(), "banana", "10"));
        invalidData.add(List.of(OperationType.PURCHASE.getCode(), "banana", "31"));
        processingService.processData(invalidData);
    }
}
