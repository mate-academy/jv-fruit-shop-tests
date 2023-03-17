package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.transaction.BalanceTransactionHandler;
import service.transaction.PurchaseTransactionHandler;
import service.transaction.ReturnTransactionHandler;
import service.transaction.SupplyTransactionHandler;
import service.transaction.TransactionHandler;

public class FruitShopServiceImplTest {
    private static final Map<FruitTransaction.Operation,
            TransactionHandler> TRANSACTION_HANDLER_MAP = new HashMap<>();
    private static final String PATH = "src" + File.separator
                                    + "test" + File.separator
                                    + "java" + File.separator
                                    + "resources";
    public static final File INPUT_FILE =
            new File(PATH + File.separator + "testInputData.csv");
    public static final File REPORT_FILE =
            new File(PATH + File.separator + "testReportFile.csv");
    public static final File EXPECTED_RESULT_FILE =
            new File(PATH + File.separator + "expectedResultFile.csv");
    private static final File NOT_EXISTED_FILE =
            new File(PATH + File.separator + "notExistedFile.csv");
    private static final File EMPTY_FILE =
            new File(PATH + File.separator + "emptyFile.csv");
    private static final File INCORRECT_DATA_FILE =
            new File(PATH + File.separator + "incorrectInputData.csv");
    private static FruitShopService fruitShopService;
    private static FruitShopServiceImpl fruitShopServiceImpl;
    private static TransactionStrategy transactionStrategy;
    private static FileReaderService fileReaderService;
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        TRANSACTION_HANDLER_MAP.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        transactionStrategy =
                new TransactionStrategyImpl(TRANSACTION_HANDLER_MAP);
        fileReaderService = new CsvFileReaderService();
        fileWriterService = new CsvFileWriterService();
        fruitShopService = new FruitShopServiceImpl(transactionStrategy,
                                                    fileReaderService,
                                                    fileWriterService);
        fruitShopServiceImpl = new FruitShopServiceImpl(transactionStrategy,
                                                        fileReaderService,
                                                        fileWriterService);
    }

    @Test
    public void getTransactionStrategy_shouldReturnValue_Ok() {
        TransactionStrategy expectedTransactionStrategy = transactionStrategy;
        TransactionStrategy actualTransactionStrategy =
                                fruitShopServiceImpl.getTransactionStrategy();
        Assert.assertEquals("Method getTransactionStrategy should return "
                        + "the value of field TransactionStrategy.",
                        expectedTransactionStrategy, actualTransactionStrategy);
    }

    @Test
    public void setTransactionStrategy_null_Ok() {
        fruitShopServiceImpl.setTransactionStrategy(null);
        TransactionStrategy expectedTransactionStrategy = null;
        TransactionStrategy actualTransactionStrategy =
                                fruitShopServiceImpl.getTransactionStrategy();
        Assert.assertEquals("Method setTransactionStrategy must "
                        + "replace value of field transactionStrategy.",
                        expectedTransactionStrategy, actualTransactionStrategy);
        fruitShopServiceImpl.setTransactionStrategy(transactionStrategy);
    }

    @Test
    public void getFileReaderService_shouldReturnValue_Ok() {
        FileReaderService expectedFileReaderService = fileReaderService;
        FileReaderService actualFileReaderService =
                fruitShopServiceImpl.getFileReaderService();
        Assert.assertEquals("Method getFileReaderService should return"
                        + "the value of field fileReaderService.",
                        expectedFileReaderService, actualFileReaderService);
    }

    @Test
    public void setFileReaderService_null_Ok() {
        fruitShopServiceImpl.setFileReaderService(null);
        FileReaderService expectedFileReaderService = null;
        FileReaderService actualFileReaderService =
                fruitShopServiceImpl.getFileReaderService();
        Assert.assertEquals("Method setFileReaderService must "
                        + "replace value of field fileReaderService.",
                        expectedFileReaderService, actualFileReaderService);
        fruitShopServiceImpl.setFileReaderService(fileReaderService);
    }

    @Test
    public void getFileWriterService_shouldReturnValue_Ok() {
        FileWriterService expectedFileWriterService = fileWriterService;
        FileWriterService actualFileWriterService =
                fruitShopServiceImpl.getFileWriterService();
        Assert.assertEquals("Method getFileWriterService should return "
                        + "the value of field fileWriterService.",
                        expectedFileWriterService, actualFileWriterService);
    }

    @Test
    public void setFileWriterService_null_Ok() {
        fruitShopServiceImpl.setFileWriterService(null);
        FileWriterService expectedFileWriterService = null;
        FileWriterService actualFileWriterService =
                fruitShopServiceImpl.getFileWriterService();
        Assert.assertEquals("Method setFileWriterService must "
                        + "replace value of field fileWriterService.",
                        expectedFileWriterService, actualFileWriterService);
        fruitShopServiceImpl.setFileWriterService(fileWriterService);
    }

    @Test
    public void generateDailyReport_nullInputFile_Ok() {
        File report = fruitShopService
                        .generateDailyReport(null, REPORT_FILE);
        List<String> expectedReport = new ArrayList<>();
        List<String> actualReport = readFile(report);
        Assert.assertEquals("For inputed null file, should"
                + "return empty report file.", expectedReport, actualReport);
        REPORT_FILE.delete();
    }

    @Test
    public void generateDailyReport_nullReportFile_NotOk() {
        try {
            fruitShopService.generateDailyReport(INPUT_FILE, null);
        } catch (RuntimeException e) {
            return;
        }
        Assert.assertEquals("Should throw RuntimeException "
                + "when null instead of report file.", true, false);
    }

    @Test
    public void generateDailyReport_nullFiles_NotOk() {
        try {
            fruitShopService.generateDailyReport(null, null);
        } catch (RuntimeException e) {
            return;
        }
        Assert.assertEquals("Should throw RuntimeException "
                + "when null instead of files.", true, false);
    }

    @Test
    public void generateDailyReport_notExistedInputFile_NotOk() {
        try {
            fruitShopService.generateDailyReport(NOT_EXISTED_FILE, REPORT_FILE);
        } catch (RuntimeException e) {
            return;
        }
        Assert.assertEquals("If input file doesn't exist, "
                        + "should throw RuntimeException.", true, false);
    }

    @Test
    public void generateDailyReport_emptyInputFile_Ok() {
        createNewFile(EMPTY_FILE);
        File report = fruitShopService
                .generateDailyReport(EMPTY_FILE, REPORT_FILE);
        List<String> expectedReport = new ArrayList<>();
        List<String> actualReport = readFile(report);
        Assert.assertEquals("For inputed empty file, should"
                + "return empty report file.", expectedReport, actualReport);
        EMPTY_FILE.delete();
        REPORT_FILE.delete();
    }

    @Test
    public void generateDailyReport_everythingFine_Ok() {
        File report = fruitShopService
                .generateDailyReport(INPUT_FILE, REPORT_FILE);
        List<String> expectedReport = readFile(EXPECTED_RESULT_FILE);
        List<String> actualReport = readFile(report);
        Assert.assertEquals("For inputed empty file, should"
                + "return empty report file.", expectedReport, actualReport);
        REPORT_FILE.delete();
    }

    @Test
    public void generateDailyReport_fileWithIncorrectData_NotOk() {
        try {
            fruitShopService.generateDailyReport(INCORRECT_DATA_FILE, REPORT_FILE);
        } catch (NoSuchElementException e) {
            return;
        }
        Assert.assertEquals("If input file contains incorrect data, "
                + "should throw NoSuchElementException.", true, false);
    }

    private List<String> readFile(File inputFile) {
        try {
            return Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can’t read file", e);
        }
    }

    private void createNewFile(File testFile) {
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can’t create file for test", e);
        }
    }
}
