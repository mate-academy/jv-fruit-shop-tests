package core.basesyntax;

import core.basesyntax.dao.DataConverter;
import core.basesyntax.dao.DataConverterImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.models.activities.ActivityHandler;
import core.basesyntax.models.activities.BalanceActivityHandler;
import core.basesyntax.models.activities.PurchaseActivityHandler;
import core.basesyntax.models.activities.ReturnActivityHandler;
import core.basesyntax.models.activities.SupplyActivityHandler;
import core.basesyntax.services.DataProcessor;
import core.basesyntax.services.DataProcessorImpl;
import core.basesyntax.services.ReportGenerator;
import core.basesyntax.services.ReportGeneratorImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {
    private static final String PATH_REPORT_TO_READ
            = "src/test/java/core/basesyntax/resources/reportToRead.csv";
    private static final String PATH_FINAL_REPORT
            = "src/test/java/core/basesyntax/resources/finalReport.csv";

    private final Map<FruitTransaction.TypeOfActivity, ActivityHandler> activityHandlerMap = Map.of(
            FruitTransaction.TypeOfActivity.BALANCE, new BalanceActivityHandler(),
            FruitTransaction.TypeOfActivity.SUPPLY, new SupplyActivityHandler(),
            FruitTransaction.TypeOfActivity.PURCHASE, new PurchaseActivityHandler(),
            FruitTransaction.TypeOfActivity.RETURN, new ReturnActivityHandler()
    );

    @Test
    public void testTypeOfActivity_FromCode_Ok() {
        Assert.assertEquals(FruitTransaction.TypeOfActivity.BALANCE,
                FruitTransaction.TypeOfActivity.fromCode("b"));
        Assert.assertEquals(FruitTransaction.TypeOfActivity.SUPPLY,
                FruitTransaction.TypeOfActivity.fromCode("s"));
    }

    @Test
    public void testTypeOfActivityFromCode_InvalidCode_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.TypeOfActivity.fromCode("x"));
    }

    @Test
    public void testCreateFruitTransactionNullType_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(null, "banana", 30));
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, null, 30));
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "", 30));
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", -10));
    }

    @Test
    public void testFruitTransaction_ToString_Ok() {
        FruitTransaction transaction = FruitTransaction.of(
                FruitTransaction.TypeOfActivity.BALANCE, "banana", 30);
        String expected = "FruitTransaction{type=BALANCE, name='banana', quantity=30}";
        Assert.assertEquals(expected, transaction.toString());
    }

    @Test
    public void testReportToRead_Ok() {
        Storage.FruitTransactionStorage.clear();
        FileReaderCsv fileReaderCsv = new FileReaderCsvImpl();
        List<String> expected = List.of("type,product,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100", "p,banana,13",
                "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
        Assert.assertEquals(expected, fileReaderCsv.readFile(PATH_REPORT_TO_READ));
    }

    @Test
    public void testReportToReadPath_NotOk() {
        FileReaderCsv fileReaderCsv = new FileReaderCsvImpl();
        Assert.assertThrows(RuntimeException.class,
                () -> fileReaderCsv.readFile("path/reportToRead.csv"));
    }

    @Test
    public void testFileWriter_Ok() {
        FileWriterCsv fileWriterCsv = new FileWriterCsvImpl();
        String expected = "TEST TO READ";
        fileWriterCsv.write(PATH_FINAL_REPORT, expected);
        try {
            String actual = Files.readString(Path.of(PATH_FINAL_REPORT));
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            Assert.fail("Failed to read from file: " + PATH_FINAL_REPORT);
        }
    }

    @Test
    public void testFileWriter_NotOk() {
        FileWriterCsv fileWriterCsv = new FileWriterCsvImpl();
        Assert.assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write("path/finalReport.csv", "TEST TO READ"));
    }

    @Test
    public void testActivityStrategy_Ok() {
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        activityHandlerMap.forEach((type, handler) ->
                Assert.assertEquals(handler, activityStrategy.getActivity(type)));
    }

    @Test
    public void testNullOrEmptyActivityStrategy_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> new ActivityStrategyImpl(new HashMap<>()));
        Assert.assertThrows(RuntimeException.class,
                () -> new ActivityStrategyImpl(null));
    }

    @Test
    public void testHandler_Ok() {
        Assert.assertEquals(10, new BalanceActivityHandler().apply(0, 10).intValue());
        Assert.assertEquals(15, new SupplyActivityHandler().apply(0, 15).intValue());
        Assert.assertEquals(20, new ReturnActivityHandler().apply(0, 20).intValue());
        Assert.assertEquals(10, new PurchaseActivityHandler().apply(20, 10).intValue());
    }

    @Test
    public void testHandler_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> new PurchaseActivityHandler().apply(10, 100));
    }

    @Test
    public void testDataConverter_Ok() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> input = List.of("type,product,quantity",
                "b,banana,20", "b,apple,20", "s,banana,30", "s,apple,40",
                "p,banana,10", "p,apple,10", "r,banana,70", "r,apple,80");
        List<FruitTransaction> expected = List.of(
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "apple", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "banana", 30),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "apple", 40),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "banana", 10),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "apple", 10),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.RETURN, "banana", 70),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.RETURN, "apple", 80));
        Assert.assertEquals(expected, dataConverter.convertToTransaction(input));
    }

    @Test
    public void testDataConverter_NotOk() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> input = List.of("type,product,quantity",
                "b,banana,20", "BALANCE,apple,20");
        Assert.assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(input));
    }

    @Test
    public void testDataProcessor_Ok() {
        Storage.FruitTransactionStorage.clear();
        DataProcessor dataProcessor =
                new DataProcessorImpl(new ActivityStrategyImpl(activityHandlerMap));
        List<FruitTransaction> input = List.of(
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "apple", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "banana", 30),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "apple", 40),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "banana", 10),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "apple", 10));
        dataProcessor.process(input);
        Assert.assertEquals(40, Storage.FruitTransactionStorage.get("banana").intValue());
        Assert.assertEquals(50, Storage.FruitTransactionStorage.get("apple").intValue());
    }

    @Test
    public void testDataProcessor_NotOk() {
        DataProcessor dataProcessor =
                new DataProcessorImpl(new ActivityStrategyImpl(activityHandlerMap));
        List<FruitTransaction> input = List.of(
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "apple", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "banana", 30),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "apple", 40),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "banana", 10000),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "apple", 10));
        Assert.assertThrows(RuntimeException.class, () -> dataProcessor.process(input));
    }

    @Test
    public void testGenerator_Ok() {
        Storage.FruitTransactionStorage.clear();
        Storage.FruitTransactionStorage.put("banana", 30);
        Storage.FruitTransactionStorage.put("apple", 40);
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String expected = "fruit,quantity\r\nbanana,30\r\napple,40\r\n";
        Assert.assertEquals(expected, reportGenerator.generate());
    }
}
