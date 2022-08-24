package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ParserService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MainTest {
    private static ParserService parserService;
    private static ReaderService readerService;
    private static ReportService reportService;
    private static WriterService writerService;
    private static FruitDao fruitDao;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    @Rule
    public ExpectedException exceptionRuleFileNotFound = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        parserService = new ParserServiceImpl();
        readerService = new ReaderServiceImpl();
        reportService = new ReportServiceImpl(fruitDao);
        writerService = new WriterServiceImpl();

        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Before
    public void setUp() throws Exception {
        fruitDao.clear();
    }

    @After
    public void tearDown() throws Exception {
        fruitDao.clear();
    }

    @Test
    public void reader_FileNotFoundNotOK() {
        final String inputFile = "./src/main/resources/notFound.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't read the file " + inputFile);
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void reader_FileIsEmptyNotOK() {
        final String inputFile = "./src/main/resources/empty.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("File " + inputFile + " is empty.");
        readerService.readFromCsvFile(inputFile);
    }

    @Test
    public void writer_FileNotFoundNotOK() {
        final String outputFile = "./src/main/notFound/someFile.txt";
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Can't write the file " + outputFile);
        writerService.writeToCsvFile("fruit,quantity", outputFile);
    }

    @Test
    public void writer_writeDataOK() {
        final String outputFile = "./src/main/resources/outputTestOK.txt";
        writerService.writeToCsvFile("fruit,quantity\r\nbanana,20\r\napple,10", outputFile);
        List<String> expected = readerService.readFromCsvFile(outputFile);
        List<String> actual = List.of("fruit,quantity", "banana,20", "apple,10");
        assertEquals(expected, actual);
    }

    @Test
    public void parser_InpuIstNullNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Input param is null.");
        parserService.parse(null);
    }

    @Test
    public void parser_InpuIsEmptyNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Input param is empty.");
        parserService.parse(List.of());
    }

    @Test
    public void parser_BadDataInFileNotOK() {
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Bad data in line: 2");
        parserService.parse(List.of("b,fruit,1", "b,,1"));
    }

    @Test
    public void reader_readFromFileOK() {
        final String inputFile = "./src/main/resources/readOk.txt";
        List<String> actual = readerService.readFromCsvFile(inputFile);
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10");
        assertEquals(expected, actual);
    }

    @Test
    public void report_CreateOK() {
        fruitDao.add("banana", 50);
        fruitDao.add("apple", 30);
        String expected = "banana,50\r\napple,30";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void balanceHandlerOK() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        assertEquals(expected, fruitDao.getAll());
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        expected.put("banana", 60);
        assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void returnHandlerOK() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 30);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 30);
        assertEquals(expected, fruitDao.getAll());
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 70);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        expected.put("banana", 100);
        assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void supplyHandlerOK() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 30);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 30);
        assertEquals(expected, fruitDao.getAll());
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 70);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        expected.put("banana", 100);
        assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void purchaseHandlerOK() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 99);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 1);
        assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void purchaseHandlerNotEnoughFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 100);
        operationStrategy.get(transaction.getOperation()).process(transaction);
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 120);
        exceptionRuleFileNotFound.expect(RuntimeException.class);
        exceptionRuleFileNotFound.expectMessage("Not enough "
                + transaction.getFruit() + " in shop.");
        operationStrategy.get(transaction.getOperation()).process(transaction);
    }

    @Test
    public void allOk() {
        final String inputFile = "./src/main/resources/inputTest.txt";
        final String outputFile = "./src/main/resources/outputTest.txt";

        List<String> dataFromFile = readerService.readFromCsvFile(inputFile);
        List<FruitTransaction> transactionList = parserService.parse(dataFromFile);
        for (FruitTransaction transaction : transactionList) {
            operationStrategy.get(transaction.getOperation()).process(transaction);
        }

        String resultReport = reportService.createReport();
        writerService.writeToCsvFile(resultReport, outputFile);
        List<String> actual = readerService.readFromCsvFile(outputFile);
        List<String> expected = List.of("banana,152", "apple,90", "blackberry,3", "mango,88");
        assertEquals(expected,actual);
    }
}
