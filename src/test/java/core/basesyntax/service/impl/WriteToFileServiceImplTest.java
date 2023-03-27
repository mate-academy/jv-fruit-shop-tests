package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReadDataService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriteToFileService;
import core.basesyntax.strategy.FruitShopStrategy;
import core.basesyntax.strategy.impl.FruitShopStrategyImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileServiceImplTest {
    private static final String FILE_DATE_NAME_OK = "FruitShopOk.csv";
    private static final String FILE_DATE_NAME_NOT_OK = "FruitShopNotOk.csv";
    private static final String FILE_REPORT_NAME = "src/main/java/resources/FruitShopReport.csv";
    private static ReadDataService readDataService;
    private static ParseService parser;
    private static WriteToFileService writeToFileService;
    private static FruitShopStrategy fruitShopStrategy;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        readDataService = new ReadDataServiceImpl();
        parser = new ParseServiceImpl();
        writeToFileService = new WriteToFileServiceImpl();
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        fruitShopStrategy = new FruitShopStrategyImpl(operationHandlerMap);
    }

    @After
    public void clearResults() {
        try {
            Files.deleteIfExists(Path.of(FILE_REPORT_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    public void testWriteToFile_Ok() {
        List<String> data = readDataService.readFromFile(FILE_DATE_NAME_OK);
        List<FruitTransaction> fruitTransactionList = parser.getFruitTransactions(data);
        for (FruitTransaction transaction : fruitTransactionList) {
            OperationHandler handler = fruitShopStrategy.get(transaction.getOperation());
            handler.handle(transaction);
        }
        String report = reportService.getReport();
        writeToFileService.writeToFile(report, FILE_REPORT_NAME);
        assertTrue(Files.exists(Path.of("src/main/java/resources/FruitShopReport.csv")));
    }

    @Test
    public void testOperationHandler_Not_Ok() {
        List<String> data = readDataService.readFromFile(FILE_DATE_NAME_NOT_OK);
        List<FruitTransaction> fruitTransactionList = parser.getFruitTransactions(data);
        try {
            for (FruitTransaction transaction : fruitTransactionList) {
                OperationHandler handler = fruitShopStrategy.get(transaction.getOperation());
                handler.handle(transaction);
            }
        } catch (RuntimeException e) {
            return;
        }
        fail("The purchase cannot be completed");
    }

    @Test
    public void testReport_OK() {
        List<String> data = readDataService.readFromFile(FILE_DATE_NAME_OK);
        List<FruitTransaction> fruitTransactionList = parser.getFruitTransactions(data);
        for (FruitTransaction transaction : fruitTransactionList) {
            OperationHandler handler = fruitShopStrategy.get(transaction.getOperation());
            handler.handle(transaction);
        }
        String actual = reportService.getReport();
        assertEquals("banana,65\r\napple,45\r\n", actual);
    }
}
