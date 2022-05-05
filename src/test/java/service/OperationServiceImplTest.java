package service;

import static org.junit.Assert.assertEquals;

import dao.StorageDaoImpl;
import db.Storage;
import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService operationService;
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static ParseService parseService;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
        parseService = new ParseServiceImpl();
        operationHandlerStrategy = new OperationHandlerStrategyImpl();
        operationService = new OperationServiceImpl(operationHandlerStrategy);
    }

    @Test
    public void calculate_emptyList_ok() {
        List<FruitTransaction> info = new ArrayList<>();
        operationService.calculate(info);
    }

    @Test
    public void calculate_correctInput_ok() {
        List<String> list = new ArrayList<>();
        list.add("b-banana-20");
        list.add("s-banana-20");
        list.add("b-apple-50");
        list.add("r-apple-10");
        list.add("p-apple-10");
        List<FruitTransaction> fruitTransactions = parseService.getInfo(list);
        operationService.calculate(fruitTransactions);
        String actual = reportService.createReport();
        String expected = "fruit,quantity\nbanana,40"
                + System.lineSeparator()
                + "apple,50"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_wrongInput_notOk() {
        Fruit banana = new Fruit("banana");
        List<FruitTransaction> list = List.of(new FruitTransaction(null, banana, 100));
        operationService.calculate(list);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
