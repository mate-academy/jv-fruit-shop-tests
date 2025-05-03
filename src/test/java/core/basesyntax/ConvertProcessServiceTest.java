package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.converter.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.DataProcessorImpl;
import core.basesyntax.service.processor.DataProcessor;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.OperationType;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConvertProcessServiceTest {
    private static Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
    private static DataConverter converter;
    private static DataProcessor processor;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.B, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.S, new SupplyOperationHandler());
        operationHandlerMap.put(OperationType.P, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.R, new ReturnOperationHandler());
        converter = new DataConverterImpl();
        processor = new DataProcessorImpl(operationHandlerMap);
    }

    @Test
    void convertData_Ok() {
        List<String> listToConvert = new ArrayList<>();
        listToConvert.add("type,fruit,quantity");
        listToConvert.add("b,banana,20");
        listToConvert.add("b,apple,100");
        listToConvert.add("s,banana,100");
        listToConvert.add("p,banana,13");

        List<List<String>> expected = new ArrayList<>();
        expected.add(List.of("b", "banana", "20"));
        expected.add(List.of("b", "apple", "100"));
        expected.add(List.of("s", "banana", "100"));
        expected.add(List.of("p", "banana", "13"));
        List<List<String>> actual = converter.convertData(listToConvert);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertDataWithSpaces_Ok() {
        List<String> listToConvert = new ArrayList<>();
        listToConvert.add("type,fruit,quantity");
        listToConvert.add("   b,banana,20");
        listToConvert.add(" b,apple,100");
        listToConvert.add("    s,banana,100");
        listToConvert.add("  p,banana,13");

        List<List<String>> expected = new ArrayList<>();
        expected.add(List.of("b", "banana", "20"));
        expected.add(List.of("b", "apple", "100"));
        expected.add(List.of("s", "banana", "100"));
        expected.add(List.of("p", "banana", "13"));
        List<List<String>> actual = converter.convertData(listToConvert);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void processConvertedData_Ok() {
        List<List<String>> convertedData = new ArrayList<>();
        convertedData.add(List.of("b", "banana", "20"));
        convertedData.add(List.of("b", "apple", "100"));
        convertedData.add(List.of("s", "banana", "100"));
        convertedData.add(List.of("p", "banana", "13"));
        convertedData.add(List.of("r", "apple", "10"));
        convertedData.add(List.of("p", "apple", "20"));
        convertedData.add(List.of("p", "banana", "5"));
        convertedData.add(List.of("s", "banana", "50"));

        processor.process(convertedData);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 152);
        expectedStorage.put("apple", 90);
        Assertions.assertEquals(expectedStorage, Storage.STOCK);
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
