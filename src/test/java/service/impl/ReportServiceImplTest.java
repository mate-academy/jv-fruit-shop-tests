package service.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.OperationType;
import model.dto.FruitRecordDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitService;
import service.FruitStrategy;
import service.OperationHandler;
import service.ReportService;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static FruitService fruitService;
    private static FruitStrategy fruitStrategy;
    private static final Map<OperationType, OperationHandler> fruitOperationsServiceMap
            = new HashMap<>();

    @BeforeClass
    public static void servicesInit() {
        fruitOperationsServiceMap.put(OperationType.BALANCE, new BalanceOperation());
        fruitOperationsServiceMap.put(OperationType.SUPPLY, new AddOperation());
        fruitOperationsServiceMap.put(OperationType.PURCHASE, new RemoveOperation());
        fruitOperationsServiceMap.put(OperationType.RETURN, new ReturnOperation());
        fruitStrategy = new FruitStrategyImpl(fruitOperationsServiceMap);
        fruitService = new FruitServiceImpl(fruitStrategy);
        reportService = new ReportServiceImpl();
    }

    @Before
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void makeReport_Ok() {
        List<FruitRecordDto> inputData = List.of(
                new FruitRecordDto(OperationType.BALANCE, "banana", 20),
                new FruitRecordDto(OperationType.BALANCE, "apple", 100));
        fruitService.saveDto(inputData);
        String actualReport = reportService.makeReport();
        String expectedReport = "fruit,quantity\nbanana,20\napple,100\n";
        assertEquals(expectedReport, actualReport);
    }
}
