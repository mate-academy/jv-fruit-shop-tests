package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.DataProcessor;
import core.basesyntax.service.DataProcessorImpl;
import core.basesyntax.service.amount.AddAmount;
import core.basesyntax.service.amount.AmountHandler;
import core.basesyntax.service.amount.SubtractAmount;
import core.basesyntax.service.files.InputFileReader;
import core.basesyntax.service.files.InputFileReaderImpl;
import core.basesyntax.service.files.InputRowParser;
import core.basesyntax.service.files.InputRowParserImpl;
import core.basesyntax.service.files.ReportWriter;
import core.basesyntax.service.files.ReportWriterImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.validation.DataValidator;
import core.basesyntax.service.validation.DataValidatorImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type RETURN = FruitRecord.Type.RETURN;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static final String CORRECT_INPUT_FILE = "src/main/resources/fruits_correct.csv";
    private static final String NON_EXISTENT_FILE = "src/main/resource/non_existent.csv";
    private static final String REPORT_FILE = "src/main/resources/report.csv";
    private static final String DATA_DIVIDER = ",";
    private static final List<FruitRecord> DATA_BASE = DataBase.db;
    private static Map<FruitRecord.Type, AmountHandler> strategies;
    private static List<String> correctInputFileData;
    private static List<String> inCorrectInputFileData;
    private static List<String> inCorrectInputFileData2;
    private static List<String> inCorrectInputFileData3;
    private static List<FruitRecord> correctFruitRecords;
    private static List<FruitRecord> correctFruitReportRecords;
    private static List<String> correctReportRecords;
    private static OperationStrategy operationStrategy;
    private static DataProcessor dataProcessor;
    private static AmountHandler addAmountHandler;
    private static AmountHandler subtractAmountHandler;
    private static InputFileReader inputFileReaderImpl;
    private static ReportWriter reportWriter;
    private static InputRowParser inputRowParser;
    private static DataValidator dataValidator;
    private static FruitsDao fruitsDao;

    @Before
    public void setUp() {
        addAmountHandler = new AddAmount();
        subtractAmountHandler = new SubtractAmount();
        inputFileReaderImpl = new InputFileReaderImpl();
        reportWriter = new ReportWriterImpl();
        inputRowParser = new InputRowParserImpl();
        dataValidator = new DataValidatorImpl();
        fruitsDao = new FruitsDaoImpl();
        correctFruitRecords = new ArrayList<>();
        correctFruitRecords.add(new FruitRecord(20, BALANCE, new Fruit("banana")));
        correctFruitRecords.add(new FruitRecord(100, BALANCE, new Fruit("apple")));
        correctFruitRecords.add(new FruitRecord(100, SUPPLY, new Fruit("banana")));
        correctFruitRecords.add(new FruitRecord(13, PURCHASE, new Fruit("banana")));
        correctFruitRecords.add(new FruitRecord(10, RETURN, new Fruit("apple")));
        correctFruitRecords.add(new FruitRecord(20, PURCHASE, new Fruit("apple")));
        correctFruitRecords.add(new FruitRecord(5, PURCHASE, new Fruit("banana")));
        correctFruitRecords.add(new FruitRecord(50, SUPPLY, new Fruit("banana")));
        correctFruitReportRecords = new ArrayList<>();
        correctFruitReportRecords.add(new FruitRecord(152, BALANCE, new Fruit("banana")));
        correctFruitReportRecords.add(new FruitRecord(90, BALANCE, new Fruit("apple")));
        correctReportRecords = new ArrayList<>();
        correctReportRecords.add("fruitRecord,quantity");
        correctReportRecords.add("banana,152");
        correctReportRecords.add("apple,90");
        strategies = new HashMap<>();
        strategies.put(BALANCE, addAmountHandler);
        strategies.put(SUPPLY, addAmountHandler);
        strategies.put(RETURN, addAmountHandler);
        strategies.put(PURCHASE, subtractAmountHandler);
        operationStrategy = new OperationStrategyImpl(strategies);
        dataProcessor = new DataProcessorImpl(operationStrategy);
        correctInputFileData = new ArrayList<>();
        correctInputFileData.add("b,banana,20");
        correctInputFileData.add("b,apple,100");
        correctInputFileData.add("s,banana,100");
        correctInputFileData.add("p,banana,13");
        correctInputFileData.add("r,apple,10");
        correctInputFileData.add("p,apple,20");
        correctInputFileData.add("p,banana,5");
        correctInputFileData.add("s,banana,50");
        inCorrectInputFileData = new ArrayList<>();
        inCorrectInputFileData.add("b,banana,20");
        inCorrectInputFileData.add("p,,");
        inCorrectInputFileData.add("s,banana,50");
        inCorrectInputFileData2 = new ArrayList<>();
        inCorrectInputFileData2.add("b,banana,20");
        inCorrectInputFileData2.add("p,banana,-10");
        inCorrectInputFileData2.add("s,banana,50");
        inCorrectInputFileData3 = new ArrayList<>();
        inCorrectInputFileData3.add("b,banana,20");
        inCorrectInputFileData3.add("p,apple,10");
        inCorrectInputFileData3.add(",banana,50");
    }

    @Test
    public void getCorrectAmountHandler_OK() {
        AmountHandler expectedBalance = strategies.get(BALANCE);
        AmountHandler expectedSupply = strategies.get(SUPPLY);
        AmountHandler expectedReturn = strategies.get(RETURN);
        AmountHandler expectedPurchase = strategies.get(PURCHASE);
        AmountHandler actualBalance = operationStrategy.get(BALANCE);
        AmountHandler actualSupply = operationStrategy.get(SUPPLY);
        AmountHandler actualReturn = operationStrategy.get(RETURN);
        AmountHandler actualPurchase = operationStrategy.get(PURCHASE);
        Assert.assertEquals("Test failed! For operation type "
                + BALANCE.name() + " you should get "
                + expectedBalance.getClass().getSimpleName()
                + " handler.",
                expectedBalance.getClass().getSimpleName(),
                actualBalance.getClass().getSimpleName());
        Assert.assertEquals("Test failed! For operation type "
                + SUPPLY.name() + " you should get "
                + expectedSupply.getClass().getSimpleName()
                + " handler.",
                expectedSupply.getClass().getSimpleName(),
                actualSupply.getClass().getSimpleName());
        Assert.assertEquals("Test failed! For operation type "
                        + RETURN.name() + " you should get "
                        + expectedReturn.getClass().getSimpleName()
                        + " handler.",
                expectedReturn.getClass().getSimpleName(),
                actualReturn.getClass().getSimpleName());
        Assert.assertEquals("Test failed! For operation type "
                        + PURCHASE.name() + " you should get "
                        + expectedPurchase.getClass().getSimpleName()
                        + " handler.",
                expectedPurchase.getClass().getSimpleName(),
                actualPurchase.getClass().getSimpleName());
    }

    @Test
    public void readDataFromCorrectFile_readFile_OK() {
        List<String> expectedFileData = new ArrayList<>(correctInputFileData);
        List<String> actualFileData = inputFileReaderImpl.readFile(CORRECT_INPUT_FILE);
        Assert.assertEquals("Test failed! Expected file data and actual file data is different!",
                expectedFileData, actualFileData);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromNonExistentFile_readFile_Not_OK() {
        inputFileReaderImpl.readFile(NON_EXISTENT_FILE);
    }

    @Test
    public void getFruitRecordsFromFileData_parse_OK() {
        List<FruitRecord> expected = new ArrayList<>(correctFruitRecords);
        List<FruitRecord> actual = inputRowParser.parse(correctInputFileData);
        Assert.assertEquals("Test failed! "
                        + "Expected fruit records and actual fruit records are different!",
                expected, actual);
    }

    @Test
    public void validateDataFromCorrectFile_validate_OK() {
        for (String row : correctInputFileData) {
            String[] inputRowData = row.split(DATA_DIVIDER);
            boolean actual = dataValidator.validate(inputRowData);
            Assert.assertTrue("Test failed! Expected result differs from actual result!", actual);
        }
    }

    @Test(expected = RuntimeException.class)
    public void validateDataFromIncorrectFile_validate_Not_OK() {
        for (String row : inCorrectInputFileData) {
            String[] inputRowData = row.split(DATA_DIVIDER);
            dataValidator.validate(inputRowData);
        }
    }

    @Test(expected = RuntimeException.class)
    public void validateDataFromIncorrectFile2_validate_Not_OK() {
        for (String row : inCorrectInputFileData2) {
            String[] inputRowData = row.split(DATA_DIVIDER);
            dataValidator.validate(inputRowData);
        }
    }

    @Test(expected = RuntimeException.class)
    public void validateDataFromIncorrectFile3_validate_Not_OK() {
        for (String row : inCorrectInputFileData3) {
            String[] inputRowData = row.split(DATA_DIVIDER);
            dataValidator.validate(inputRowData);
        }
    }

    @Test
    public void processCorrectFruitRecords_OK() {
        boolean actual = dataProcessor.processData(correctFruitRecords);
        Assert.assertTrue("Test failed! Expected result differs from actual result!", actual);
    }

    @Test
    public void subtractCorrectAmount_apply_OK() {
        FruitRecord newSupplyRecord = new FruitRecord(20,
                SUPPLY, new Fruit("banana"));
        FruitRecord newPurchaseRecord = new FruitRecord(13,
                PURCHASE, new Fruit("banana"));
        addAmountHandler.apply(newSupplyRecord);
        Assert.assertTrue(subtractAmountHandler.apply(newPurchaseRecord));
    }

    @Test(expected = RuntimeException.class)
    public void subtractIncorrectAmount_apply_No_OK() {
        FruitRecord newPurchaseRecord = new FruitRecord(13,
                PURCHASE, new Fruit("banana"));
        subtractAmountHandler.apply(newPurchaseRecord);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToNonExistentDirectory_writeReportToFile_Not_OK() {
        DATA_BASE.addAll(correctFruitRecords);
        reportWriter.writeReportToFile(NON_EXISTENT_FILE);
    }

    @Test
    public void writeReportToExistentDirectory_writeReportToFile_OK() {
        DATA_BASE.addAll(correctFruitReportRecords);
        reportWriter.writeReportToFile(REPORT_FILE);
        List<String> result;
        try {
            result = Files.readAllLines(Paths.get(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read input file, " + e);
        }
        List<String> expected = correctReportRecords;
        List<String> actual = result;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addNewRecordToDataBase_addRecord_OK() {
        FruitRecord newRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        Assert.assertTrue("Test failed! Expected true!",
                fruitsDao.addRecord(newRecord));
        List<FruitRecord> expected = new ArrayList<>();
        expected.add(newRecord);
        List<FruitRecord> actual = DATA_BASE;
        Assert.assertEquals("Test failed! Expected record differs from actual record!",
                expected, actual);
    }

    @Test
    public void updateExistingRecord_updateRecord_OK() {
        FruitRecord newRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord anotherRecord = new FruitRecord(45, SUPPLY, new Fruit("banana"));
        DATA_BASE.add(newRecord);
        Assert.assertTrue("Test failed! Expected true!",
                fruitsDao.updateRecord(anotherRecord));
        List<FruitRecord> expected = new ArrayList<>();
        expected.add(anotherRecord);
        List<FruitRecord> actual = DATA_BASE;
        Assert.assertEquals("Test failed! Expected record differs from actual record!",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void updateNonExistingRecord_updateRecord_Not_OK() {
        FruitRecord newRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        fruitsDao.updateRecord(newRecord);
    }

    @Test
    public void getExistingRecord_getRecord_OK() {
        FruitRecord expected = new FruitRecord(20, BALANCE, new Fruit("banana"));
        DATA_BASE.add(expected);
        FruitRecord actual = fruitsDao.getRecord(expected);
        Assert.assertEquals("Test failed! Expected record differs from actual record!",
                expected, actual);
    }

    @Test
    public void getNonExistingRecord_getRecord_OK() {
        FruitRecord expected = new FruitRecord(0, BALANCE, new Fruit("banana"));
        FruitRecord actual = fruitsDao.getRecord(expected);
        Assert.assertEquals("Test failed! Expected record differs from actual record!",
                expected, actual);
    }

    @Test
    public void getAllRecordsFromDataBase_OK() {
        FruitRecord newRecord = new FruitRecord(21, BALANCE, new Fruit("banana"));
        List<FruitRecord> expected = new ArrayList<>(correctFruitRecords);
        expected.remove(5);
        expected.set(0, newRecord);
        DATA_BASE.addAll(correctFruitRecords);
        DATA_BASE.remove(5);
        DATA_BASE.set(0, newRecord);
        List<FruitRecord> actual = fruitsDao.getRecords();
        Assert.assertEquals("Test failed! Expected records differ from actual records!",
                expected, actual);
    }

    @Test
    public void checkFruitsHashCodeEquality_hashCode_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("banana");
        int expected = fruit.hashCode();
        int actual = newFruit.hashCode();
        Assert.assertEquals("Test failed! Fruits hashcodes should be equal!",
                expected, actual);
    }

    @Test
    public void checkFruitsHashCodeEquality_hashCode_Not_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("apple");
        int expected = fruit.hashCode();
        int actual = newFruit.hashCode();
        Assert.assertNotEquals("Test failed! Fruits hashcodes should be different!",
                expected, actual);
    }

    @Test
    public void checkFruitsEquality_equals_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("banana");
        Assert.assertEquals("Test failed! Fruits should be equal!",
                fruit, newFruit);
    }

    @Test
    public void checkFruitsEquality_equals_Not_OK() {
        Fruit fruit = new Fruit("banana");
        Fruit newFruit = new Fruit("apple");
        Assert.assertNotEquals("Test failed! Fruits should be equal!",
                fruit, newFruit);
    }

    @Test
    public void checkFruitRecordsHashCodeEquality_hashCode_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        int expected = fruitrecord.hashCode();
        int actual = newFruitRecord.hashCode();
        Assert.assertEquals("Test failed! Fruit records' hashcodes should be equal!",
                expected, actual);
    }

    @Test
    public void checkFruitRecordsHashCodeEquality_hashCode_Not_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(15, SUPPLY, new Fruit("apple"));
        int expected = fruitrecord.hashCode();
        int actual = newFruitRecord.hashCode();
        Assert.assertNotEquals("Test failed! Fruit records' hashcodes should be unequal!",
                expected, actual);
    }

    @Test
    public void checkFruitRecordsEquality_equals_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        Assert.assertEquals("Test failed! Fruit records should be equal!",
                fruitrecord, newFruitRecord);
    }

    @Test
    public void checkFruitRecordsEquality_equals_Not_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(15, SUPPLY, new Fruit("apple"));
        Assert.assertNotEquals("Test failed! Fruit records should be unequal!",
                fruitrecord, newFruitRecord);
    }

    @After
    public void afterEachTest() {
        DATA_BASE.clear();
        PrintWriter writer;
        try {
            writer = new PrintWriter(REPORT_FILE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Report file not found, " + e);
        }
        writer.print("");
        writer.close();
    }
}
