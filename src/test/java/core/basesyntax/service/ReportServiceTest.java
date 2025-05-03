package core.basesyntax.service;

import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.activity.Activity;
import core.basesyntax.service.activity.BalanceActivity;
import core.basesyntax.service.activity.PurchaseActivity;
import core.basesyntax.service.activity.ReturnActivity;
import core.basesyntax.service.activity.SupplyActivity;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String FILE_INPUT_NAME = "src/test/resources/listOfFruit.csv";
    private static final String FILE_OUTPUT_NAME = "src/test/resources/outputReport.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources.listOfFruit.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/emptyFile.csv";
    private static final String FILE_NAME_WITH_NEGATIVE_AMOUNT = "src/test/resources/negatives.csv";
    private static final String FILE_NAME_WITH_IMPOSSIBLE_OPERATION
            = "src/test/resources/impossibleOperation.csv";
    private static final String WRONG_OUTPUT_FILE_NAME = "";
    private static FruitDao fruitDao;
    private static ActivityStrategy activityStrategy;
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() throws Exception {
        Map<Fruit.Type, Activity> typeActivityMap = new HashMap<>();
        typeActivityMap.put(Fruit.Type.BALANCE, new BalanceActivity());
        typeActivityMap.put(Fruit.Type.PURCHASE, new PurchaseActivity());
        typeActivityMap.put(Fruit.Type.RETURN, new ReturnActivity());
        typeActivityMap.put(Fruit.Type.SUPPLY, new SupplyActivity());

        fruitDao = new FruitDaoImpl();
        activityStrategy = new ActivityStrategyImpl(typeActivityMap);
        reportService = new ReportServiceImpl(fruitDao, activityStrategy);
    }

    @Test
    public void reportTest_Ok() {
        List<String> expectedList = getListForCompareToReport();
        reportService.report(FILE_INPUT_NAME, FILE_OUTPUT_NAME);
        List<String> actualList = readDataFromReport(FILE_OUTPUT_NAME);
        assertArrayEquals(expectedList.toArray(), actualList.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void reportTest_WrongInputFilePath() {
        reportService.report(WRONG_FILE_NAME, FILE_OUTPUT_NAME);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void reportTest_EmptyFile() {
        reportService.report(EMPTY_FILE_NAME, FILE_OUTPUT_NAME);
    }

    @Test
    public void getDataFromCsvAndPutItsNamesToDB_Ok() {
        invokePrivateMethod("getDataFromCsvAndPutItsNamesToDB", reportService, FILE_INPUT_NAME);
        List<String> actualNames = new ArrayList<>();
        for (Fruit fruit : fruitDao.getAll()) {
            actualNames.add(fruit.getName());
        }
        List<String> expectedNames = List.of("banana", "apple");
        assertArrayEquals(expectedNames.toArray(), actualNames.toArray());
    }

    @Test
    public void fillDB_withValidData() {
        // Make expected result
        Fruit fruit1 = new Fruit();
        fruit1.setName("apple");
        fruit1.setAmount(90);
        Fruit fruit2 = new Fruit();
        fruit2.setName("banana");
        fruit2.setAmount(152);
        List<Fruit> expectedList = new ArrayList<>();
        Collections.addAll(expectedList, fruit1, fruit2);
        // Fill Storage with names of Fruit
        Fruit fruit3 = new Fruit();
        fruit3.setName("apple");
        Fruit fruit4 = new Fruit();
        fruit4.setName("banana");
        Collections.addAll(Storage.listOfRecords, fruit3, fruit4);
        // Call method with reflection
        invokePrivateMethod("fillDB_withValidData", reportService, FILE_INPUT_NAME);
        // Get actual result and compare it with expected one
        List<Fruit> actualList = fruitDao.getAll();
        assertArrayEquals(expectedList.toArray(), actualList.toArray());
    }

    // Test method: fillDB_withValidData
    @Test(expected = RuntimeException.class)
    public void negativeAmount_isNotValid() {
        invokePrivateMethod("fillDB_withValidData",
                reportService, FILE_NAME_WITH_NEGATIVE_AMOUNT);
    }

    // Test method: fillDB_withValidData
    @Test(expected = RuntimeException.class)
    public void impossibleOperationTest_isNotValid() {
        // Fill Storage with names of Fruit
        Fruit fruit3 = new Fruit();
        fruit3.setName("apple");
        Fruit fruit4 = new Fruit();
        fruit4.setName("banana");
        Collections.addAll(Storage.listOfRecords, fruit3, fruit4);
        invokePrivateMethod("fillDB_withValidData",
                reportService, FILE_NAME_WITH_IMPOSSIBLE_OPERATION);
    }

    @Test
    public void writeReportTest_Ok() {
        // Fill Storage with data
        Fruit fruit1 = new Fruit();
        fruit1.setName("apple");
        fruit1.setAmount(90);
        Fruit fruit2 = new Fruit();
        fruit2.setName("banana");
        fruit2.setAmount(152);
        Collections.addAll(Storage.listOfRecords, fruit1, fruit2);
        invokePrivateMethod("writeReport", reportService, FILE_OUTPUT_NAME);
        List<String> actualList = readDataFromReport(FILE_OUTPUT_NAME);
        List<String> expectedList = getListForCompareToReport();
        assertArrayEquals(expectedList.toArray(), actualList.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void incorrectFilePath() { // Test method writeReport: getDataFromDBAndWriteThemIntoFile
        invokePrivateMethod("writeReport",
                reportService, WRONG_OUTPUT_FILE_NAME);
    }

    @After
    public void tearDown() throws Exception {
        Storage.listOfRecords.clear();
        File usedFile = new File(FILE_OUTPUT_NAME);
        usedFile.delete();
    }

    private static List<String> readDataFromReport(String fileOutputName) {
        List<String> list = new ArrayList<>();
        try {
            list = Files.readAllLines(Path.of(fileOutputName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from the file named: " + fileOutputName, e);
        }
        return list;
    }

    private static List<String> getListForCompareToReport() {
        List<String> list = new ArrayList<>();
        list.add("fruit,quantity");
        list.add("apple,90");
        list.add("banana,152");
        return list;
    }

    private static void invokePrivateMethod(String nameOfMethod, Object objForCall, String arg) {
        try {
            Method method = ReportServiceImpl.class
                    .getDeclaredMethod(nameOfMethod, String.class);
            method.setAccessible(true);
            method.invoke(objForCall, arg);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("There is a problem with method: " + nameOfMethod, e);
        }
    }
}
