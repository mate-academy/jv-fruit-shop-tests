package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static FruitShopService fruitShopService;
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
    }

    @Test
    public void accessors_Ok() {
        FruitShopServiceImpl testFruitShopService =
                new FruitShopServiceImpl(null, null, null);
        TransactionStrategy expectedTransactionStrategy = null;
        TransactionStrategy actualTransactionStrategy =
                                testFruitShopService.getTransactionStrategy();
        Assert.assertEquals("Method getTransactionStrategy should return null.",
                expectedTransactionStrategy, actualTransactionStrategy);

        FileReaderService expectedFileReaderService = null;
        FileReaderService actualFileReaderService =
                                testFruitShopService.getFileReaderService();
        Assert.assertEquals("Method getFileReaderService should return null",
                expectedFileReaderService, actualFileReaderService);

        FileWriterService expectedFileWriterService = null;
        FileWriterService actuaslFileWriterService =
                                testFruitShopService.getFileWriterService();
        Assert.assertEquals("Method getFileWriterService should return null",
                expectedFileWriterService, actuaslFileWriterService);

        expectedTransactionStrategy = transactionStrategy;
        testFruitShopService.setTransactionStrategy(expectedTransactionStrategy);
        actualTransactionStrategy = testFruitShopService.getTransactionStrategy();
        Assert.assertEquals("Method getTransactionStrategy should return"
                + "the value of field TransactionStrategy",
                expectedTransactionStrategy, actualTransactionStrategy);

        expectedFileReaderService = fileReaderService;
        testFruitShopService.setFileReaderService(expectedFileReaderService);
        actualFileReaderService = testFruitShopService.getFileReaderService();
        Assert.assertEquals("Method getFileReaderService should return"
                + "the value of field FileReaderService",
                expectedFileReaderService, actualFileReaderService);

        expectedFileWriterService = fileWriterService;
        testFruitShopService.setFileWriterService(expectedFileWriterService);
        actuaslFileWriterService = testFruitShopService.getFileWriterService();
        Assert.assertEquals("Method getFileWriterService should return"
                + "the value of field FileWriterService",
                expectedFileWriterService, actuaslFileWriterService);
    }

    @Test
    public void generateDailyReport_nullInputFile_Ok() {
        File report = fruitShopService
                        .generateDailyReport(null, REPORT_FILE);
        List<String> expectedReport = new ArrayList<>();
        List<String> actualReport = readFile(report);
        Assert.assertEquals("For inputed null file, should" +
                "return empty report file.", expectedReport, actualReport);
    }

    @Test
    public void generateDailyReport_nullReportFile_NotOk() {
        try {
            fruitShopService.generateDailyReport(INPUT_FILE, REPORT_FILE);
        } catch (RuntimeException e) {
            return;
        }
        Assert.assertEquals("Should throw RuntimeException "
                + "when null instead of report file.", true, false);
    }

    private List<String> readFile(File inputFile) {
        try {
            return Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can’t read file", e);
        }
    }
}