package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.FruitStorage;
import core.basesyntax.service.DataProcessor;
import core.basesyntax.service.DataProcessorImpl;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportCreatorImpl;
import core.basesyntax.service.amount.AdditionHandler;
import core.basesyntax.service.amount.AmountHandler;
import core.basesyntax.service.amount.SubtractionHandler;
import core.basesyntax.service.files.FileReader;
import core.basesyntax.service.files.FileReaderImpl;
import core.basesyntax.service.files.ReportWriter;
import core.basesyntax.service.files.ReportWriterImpl;
import core.basesyntax.service.files.RowParser;
import core.basesyntax.service.files.RowParserImpl;
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
import java.util.Arrays;
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
    private static final String REPORT_HEADER = "fruitRecord,quantity";
    private static final String[] DATA_ROW = new String[]{"b", "banana", "10"};
    private static final String[] INCORRECT_ROW_LENGTH = new String[]{"banana"};
    private static final String[] INCORRECT_FRUIT_AMOUNT = new String[]{"p", "banana", "-10"};
    private static final String[] EMPTY_FRUIT_NAME = new String[]{"p", "", "10"};
    private static final String[] EMPTY_OPERATION_TYPE = new String[]{"", "banana", "10"};
    private static final List<FruitRecord> DATA_BASE = DataBase.DB;
    private static final Map<String, Integer> FRUIT_COUNT = FruitStorage.FRUIT_COUNT;
    private static Map<FruitRecord.Type, AmountHandler> strategies;
    private static List<String> fileData;
    private static List<FruitRecord> fruitRecords;
    private static List<String> reportRecords;
    private static OperationStrategy operationStrategy;
    private static DataProcessor dataProcessor;
    private static AmountHandler additionHandler;
    private static AmountHandler subtractionHandler;
    private static FileReader fileReaderImpl;
    private static ReportWriter reportWriter;
    private static RowParser rowParser;
    private static DataValidator dataValidator;
    private static FruitDao fruitDao;

    @Before
    public void setUp() {
        additionHandler = new AdditionHandler();
        subtractionHandler = new SubtractionHandler();
        fileReaderImpl = new FileReaderImpl();
        reportWriter = new ReportWriterImpl();
        rowParser = new RowParserImpl();
        dataValidator = new DataValidatorImpl();
        fruitDao = new FruitDaoImpl();
        fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(20, BALANCE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(100, BALANCE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(100, SUPPLY, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(13, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(10, RETURN, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(20, PURCHASE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(5, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(50, SUPPLY, new Fruit("banana")));
        reportRecords = new ArrayList<>();
        reportRecords.add("banana,152");
        reportRecords.add("apple,90");
        strategies = new HashMap<>();
        strategies.put(BALANCE, additionHandler);
        strategies.put(SUPPLY, additionHandler);
        strategies.put(RETURN, additionHandler);
        strategies.put(PURCHASE, subtractionHandler);
        operationStrategy = new OperationStrategyImpl(strategies);
        dataProcessor = new DataProcessorImpl();
        fileData = new ArrayList<>();
        fileData.add("b,banana,20");
        fileData.add("b,apple,100");
        fileData.add("s,banana,100");
        fileData.add("p,banana,13");
        fileData.add("r,apple,10");
        fileData.add("p,apple,20");
        fileData.add("p,banana,5");
        fileData.add("s,banana,50");
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
        List<String> expectedFileData = fileData;
        List<String> actualFileData = fileReaderImpl.readFile(CORRECT_INPUT_FILE);
        Assert.assertEquals("Test failed! Expected file data and actual file data is different!",
                expectedFileData, actualFileData);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromNonExistentFile_readFile_Not_OK() {
        fileReaderImpl.readFile(NON_EXISTENT_FILE);
    }

    @Test
    public void getFruitRecordsFromFileData_parse_OK() {
        List<FruitRecord> expected = fruitRecords;
        List<FruitRecord> actual = rowParser.parse(fileData);
        Assert.assertEquals("Test failed! "
                        + "Expected fruit records and actual fruit records are different!",
                expected, actual);
    }

    @Test
    public void validateDataFromCorrectFile_validate_OK() {
        Assert.assertTrue("Test failed! Expected result differs from actual result!",
                dataValidator.validate(DATA_ROW));
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectRowLength_validate_Not_OK() {
        dataValidator.validate(INCORRECT_ROW_LENGTH);
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectFruitAmount_validate_Not_OK() {
        dataValidator.validate(INCORRECT_FRUIT_AMOUNT);
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectFruitName_validate_Not_OK() {
        dataValidator.validate(EMPTY_FRUIT_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void validateIncorrectOperationType_validate_Not_OK() {
        dataValidator.validate(EMPTY_OPERATION_TYPE);
    }

    @Test
    public void processFruitRecords_OK() {
        Assert.assertTrue("Test failed! The operation should return TRUE",
                dataProcessor.processData(fruitRecords));
        List<FruitRecord> expected = fruitRecords;
        List<FruitRecord> actual = fruitDao.getRecords();
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @Test
    public void subtractCorrectAmount_apply_OK() {
        FruitRecord newSupplyRecord = new FruitRecord(20,
                SUPPLY, new Fruit("banana"));
        FruitRecord newPurchaseRecord = new FruitRecord(13,
                PURCHASE, new Fruit("banana"));
        additionHandler.apply(newSupplyRecord);
        subtractionHandler.apply(newPurchaseRecord);
        int expected = 7;
        int actual = FRUIT_COUNT.get("banana");
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void subtractIncorrectAmount_apply_No_OK() {
        FruitRecord newPurchaseRecord = new FruitRecord(13,
                PURCHASE, new Fruit("banana"));
        subtractionHandler.apply(newPurchaseRecord);
    }

    @Test
    public void createFruitRecordsReport_createReport_OK() {
        DATA_BASE.addAll(fruitRecords);
        ReportCreator reportCreator = new ReportCreatorImpl(operationStrategy);
        List<String> expected = reportRecords;
        List<String> actual = reportCreator.createReport();
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToNonExistentDirectory_writeReportToFile_Not_OK() {
        DATA_BASE.addAll(fruitRecords);
        reportWriter.writeReportToFile(NON_EXISTENT_FILE, reportRecords);
    }

    @Test
    public void writeReportToExistentDirectory_writeReportToFile_OK() {
        reportWriter.writeReportToFile(REPORT_FILE, reportRecords);
        List<String> result;
        try {
            result = Files.readAllLines(Paths.get(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read input file, " + e);
        }
        reportRecords.add(0,REPORT_HEADER);
        List<String> expected = reportRecords;
        List<String> actual = result;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addNewRecordToDataBase_addRecord_OK() {
        FruitRecord newRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        Assert.assertTrue("Test failed! Expected true!",
                fruitDao.addRecord(newRecord));
        List<FruitRecord> expected = Arrays.asList(newRecord);
        List<FruitRecord> actual = DATA_BASE;
        Assert.assertEquals("Test failed! Expected record differs from actual record!",
                expected, actual);
    }

    @Test
    public void getAllRecordsFromDataBase_OK() {
        FruitRecord newRecord = new FruitRecord(21, BALANCE, new Fruit("banana"));
        List<FruitRecord> expected = new ArrayList<>(fruitRecords);
        expected.remove(5);
        expected.set(0, newRecord);
        DATA_BASE.addAll(fruitRecords);
        DATA_BASE.remove(5);
        DATA_BASE.set(0, newRecord);
        List<FruitRecord> actual = fruitDao.getRecords();
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
        FRUIT_COUNT.clear();
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
