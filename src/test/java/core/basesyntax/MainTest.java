package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.CsvFileReader;
import core.basesyntax.dao.CsvFileReaderImpl;
import core.basesyntax.dao.ReportDao;
import core.basesyntax.dao.ReportDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.FruitTransactionServiceImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.ReportServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
    private static final String TEST_FILE = "src/test/resources/fromFileTest.csv";
    private static final String TITLE_FOR_RESULT = "fruit,quantity";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String PUNCTUATION_MARK = ",";
    private final CsvFileReader csvFileReader = new CsvFileReaderImpl();
    private final ReportDao reportDao = new ReportDaoImpl();
    private final ReportService reportService = new ReportServiceImpl();
    private Map<Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;
    private FruitTransactionService fruitTransactionService;

    @Test
    public void readTransactionsTestValidData_Ok() {
        List<FruitTransaction> actual
                = csvFileReader.readTransactions(TEST_FILE);
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void readTransactionsTestNoValidPath_NotOk() throws RuntimeException {
        String noValidPath = "ndbkam.sxc";
        csvFileReader.readTransactions(noValidPath);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToCsvFileNoValidPath_NotOk() throws RuntimeException {
        String report = "It`s some message";
        String toFileName = "src/mai/resources/toFile.csv";
        File toFile = new File(toFileName);
        reportDao.writeReportToCsvFile(report, toFile);
    }

    @Test
    public void writeReportToCsvFileValidData_Ok() throws IOException {
        String report = "It`s a message";
        String toFileNameExpected = "src/test/resources/toFileTestExpected.csv";
        String toFileNameActual = "src/test/resources/toFileTest.csv";
        File toFile = new File(toFileNameActual);
        reportDao.writeReportToCsvFile(report, toFile);
        assertEquals(Files.readAllLines(Path.of(toFileNameExpected)).toString(),
                Files.readAllLines(Path.of(toFileNameActual)).toString());
    }

    @Test
    public void addFruitsToStorage_Ok() {
        String fruitName = "coconut";
        int fruitQuantity = 20;

        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(fruitName, fruitQuantity);
        Storage.reportMap.put(fruitName, fruitQuantity);
        assertTrue(Storage.reportMap.containsKey(fruitName));
    }

    @Test
    public void createFruitTransaction_Ok() {
        String fruitName = "coconut";
        int fruitQuantity = 15;

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.BALANCE);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        assertNotNull(fruitTransactionExpected.getOperation());
        assertNotNull(fruitTransactionExpected.getFruit());
        assertNotNull((Integer) (fruitTransactionExpected.getQuantity()));
    }

    @Test
    public void getBalanceEnum_Ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.getByCode("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyEnum_Ok() {
        Operation expected = Operation.SUPPLY;
        Operation actual = Operation.getByCode("s");
        assertEquals(expected, actual);
    }

    @Test
    public void getPurchaseEnum_Ok() {
        Operation expected = Operation.PURCHASE;
        Operation actual = Operation.getByCode("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getReturnEnum_Ok() {
        Operation expected = Operation.RETURN;
        Operation actual = Operation.getByCode("r");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnumWithNotValidData_NotOk() {
        Operation actual = Operation.getByCode("y");
        assertNull(actual);
    }

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @Test
    public void checkBalanceOperationHandler_Ok() {
        String fruitName = "peach";
        int fruitQuantity = 5;

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.BALANCE);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        assertTrue(Storage.reportMap.containsKey(fruitName));
    }

    @Test
    public void checkPurchaseOperationHandler_Ok() {
        String fruitName = "banana";
        int fruitQuantity = 7;

        Storage.reportMap.put(fruitName, fruitQuantity);

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.PURCHASE);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        int fruitQuantityExpected = fruitQuantity - fruitQuantity;

        assertEquals(fruitQuantityExpected, (int) Storage.reportMap.get(fruitName));
    }

    @Test
    public void checkReturnOperationHandler_Ok() {
        String fruitName = "orange";
        int fruitQuantity = 12;

        Storage.reportMap.put(fruitName, fruitQuantity);

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.RETURN);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        int fruitQuantityExpected = fruitQuantity + fruitQuantity;

        assertEquals(fruitQuantityExpected, (int) Storage.reportMap.get(fruitName));
    }

    @Test
    public void checkSupplyOperationHandler_Ok() {
        String fruitName = "watermelon";
        int fruitQuantity = 8;

        Storage.reportMap.put(fruitName, fruitQuantity);

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.SUPPLY);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        int fruitQuantityExpected = fruitQuantity + fruitQuantity;

        assertEquals(fruitQuantityExpected, (int) Storage.reportMap.get(fruitName));
    }

    @Test
    public void createReportString_Ok() {
        String firstFruit = "orange";
        int quantityFirstFruit = 20;

        String secondFruit = "watermelon";
        int quantitySecondFruit = 15;

        StringBuilder expectedStringBuilder = new StringBuilder(TITLE_FOR_RESULT);
        expectedStringBuilder.append(NEW_LINE)
                .append(firstFruit)
                .append(PUNCTUATION_MARK)
                .append(quantityFirstFruit)
                .append(NEW_LINE)
                .append(secondFruit)
                .append(PUNCTUATION_MARK)
                .append(quantitySecondFruit);
        String expected = expectedStringBuilder.toString();

        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(firstFruit, quantityFirstFruit);
        reportMap.put(secondFruit, quantitySecondFruit);
        String actual = reportService.getReportData(reportMap);

        assertEquals(expected, actual);
    }
}
