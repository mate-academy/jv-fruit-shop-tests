package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Map<OperationType, OperationHandler> testMap;

    @BeforeClass
    public static void beforeClass() {
        testMap = new HashMap<>();
        testMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        testMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        testMap.put(OperationType.RETURN, new AddOperationHandler());
        testMap.put(OperationType.SUPPLY, new AddOperationHandler());
        reportService = new ReportServiceImpl();
    }

    @After
    public void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    public void createEmptyReport_Ok() {
        String report = reportService.getReport();
        assertEquals("fruit,quantity", report);
    }

    @Test
    public void createReport_Ok() {
        Parser parser = new ParserImpl(new ValidatorImpl());
        FruitService fruitService = new FruitServiceImpl(testMap);
        FileReader reader = new FileReaderImpl();
        List<Transaction> transactionList = parser.parseLines(reader
                .readFromFile("src/test/java/testresources/test_input_report.csv"));
        fruitService.applyOperations(transactionList);
        String report = reportService.getReport();
        assertEquals("fruit,quantity\n" + "banana,152\n" + "apple,90", report);
    }

    @Test
    public void createWrongReport_Ok() {
        FruitService fruitService = new FruitServiceImpl(testMap);
        FileReader reader = new FileReaderImpl();
        Parser parser = new ParserImpl(new ValidatorImpl());
        List<Transaction> transactionList = parser.parseLines(reader
                .readFromFile("src/test/java/testresources/empty_input.csv"));
        fruitService.applyOperations(transactionList);
        String report = reportService.getReport();
        assertEquals("fruit,quantity", report);
    }
}
