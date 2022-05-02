package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService operationService;
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static ParseService parseService;
    private static ReportService reportService;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportServiceImpl(storageDao);
        parseService = new ParseServiceImpl();
        operationHandlerStrategy = new OperationHandlerStrategyImpl();
        operationService = new OperationServiceImpl(operationHandlerStrategy);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }

    @Test(expected = RuntimeException.class)
    public void calculate_Empty_Input_NotOk() {
        List<String> list = new ArrayList<>();
        List<FruitTransaction> info = parseService.getInfo(list);
        operationService.calculate(info);
    }

    @Test
    public void calculate_Correct_Input_Ok() {
        List<String> list = new ArrayList<>();
        list.add("b-banana-20");
        list.add("s-banana-20");
        list.add("b-apple-50");
        list.add("r-apple-10");
        list.add("p-apple-10");
        List<FruitTransaction> fruitTransactions = parseService.getInfo(list);
        operationService.calculate(fruitTransactions);
        String actual = reportService.report();
        String expected = "fruit,quantity\nbanana,40"
                + System.lineSeparator()
                + "apple,50"
                +System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_Wrong_Input_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("b-banana-20");
        list.add("p-banana-21");
        List<FruitTransaction> fruitTransactions = parseService.getInfo(list);
        operationService.calculate(fruitTransactions);
    }
}
