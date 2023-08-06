package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.ActionStrategy;
import core.basesyntax.services.CreateTaskService;
import core.basesyntax.services.ParseCsvService;
import core.basesyntax.services.ProcessStoreService;
import core.basesyntax.services.ReadFileService;
import core.basesyntax.services.ReportCsvService;
import core.basesyntax.services.WriteFileService;
import core.basesyntax.services.actions.ActionHandler;
import core.basesyntax.services.actions.BalanceActionHandler;
import core.basesyntax.services.actions.PurchaseActionHandler;
import core.basesyntax.services.actions.ReturnActionHandler;
import core.basesyntax.services.actions.SupplyActionHandler;
import core.basesyntax.services.impl.ActionStrategyImpl;
import core.basesyntax.services.impl.CreateTaskServiceImpl;
import core.basesyntax.services.impl.ParseCsvServiceImpl;
import core.basesyntax.services.impl.ProcessStoreServiceImpl;
import core.basesyntax.services.impl.ReadFileServiceImpl;
import core.basesyntax.services.impl.ReportCsvServiceImpl;
import core.basesyntax.services.impl.WriteFileServiceImpl;
import core.basesyntax.util.ConstantsForCsvParse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkWithStore {

    private static Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTest;
    private static ActionStrategy actionStrategyTest;
    private static ParseCsvService parseCsvServiceTest;
    private static ProcessStoreService handleProcessTest;
    private static ReportCsvService reportCsvServiceTest;
    private static ReadFileService readFileServiceTest;
    private static WriteFileService writeFileServiceTest;
    private static Storage fruitDB;
    private static String[] defaultCorrectData;
    private static List<String[]> defaultCorrectParseData;
    private static CreateTaskService createTaskServiceTest;

    @BeforeAll
    static void createHandleStrategy() {
        fruitDB = new Storage();
        actionHandlerMapTest = new HashMap<>();
        actionHandlerMapTest.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(fruitDB));
        actionHandlerMapTest.put(FruitTransaction.ActionType.SUPPLY,
                new SupplyActionHandler(fruitDB));
        actionHandlerMapTest.put(FruitTransaction.ActionType.PURCHASE,
                new PurchaseActionHandler(fruitDB));
        actionHandlerMapTest.put(FruitTransaction.ActionType.RETURN,
                new ReturnActionHandler(fruitDB));
        actionStrategyTest = new ActionStrategyImpl(actionHandlerMapTest);
        handleProcessTest = new ProcessStoreServiceImpl(actionStrategyTest);
        readFileServiceTest = new ReadFileServiceImpl();
        parseCsvServiceTest = new ParseCsvServiceImpl();
        createTaskServiceTest = new CreateTaskServiceImpl();

        defaultCorrectData = new String[] {
                "b,banana,120",
                "b,apple,130",
                "r,banana,40",
                "s,banana,20",
                "p,apple,50",
                "p,banana,40"
        };

        defaultCorrectParseData = new ArrayList<>();
        for (String str : defaultCorrectData) {
            defaultCorrectParseData.add(str.split(ConstantsForCsvParse.COMMA));
        }
        reportCsvServiceTest = new ReportCsvServiceImpl(fruitDB);
        writeFileServiceTest = new WriteFileServiceImpl();
    }

    @BeforeEach
    void cleanStorage() {
        fruitDB.clean();
    }

    @Test
    void isPathEmpty_notOk() {
        assertThrows(ValidationDataException.class, () -> new ReadFileServiceImpl().read(""));
    }

    @Test
    void isFileEmpty_notOk() {
        assertThrows(ValidationDataException.class,
                () -> readFileServiceTest
                        .read("resources/testFiles/inputFileTestEmpty.csv"));
    }

    @Test
    void isNotCorrectPath_notOk() {
        assertThrows(ValidationDataException.class,
                () -> readFileServiceTest
                .read("resources/newFolder/inputFileTestEmpty.csv"));
    }

    @Test
    void isPathRightAndFilledFile_ok() {
        String[] actual = readFileServiceTest
                .read("resources/testFiles/inputFileTestCorrectLines.csv");
        String[] expected = defaultCorrectData;
        assertArrayEquals(expected, actual);
    }

    @Test
    void isParseNotValidData_notOk() {
        String[] actual = readFileServiceTest
                .read("resources/testFiles/inputFileTestWrongLine.csv");
        assertThrows(ValidationDataException.class,
                () -> parseCsvServiceTest.parse(actual));
    }

    @Test
    void isParseValidData_ok() {
        String[] dataFile = readFileServiceTest
                .read("resources/testFiles/inputFileTestCorrectLines.csv");
        List<String[]> actual = parseCsvServiceTest.parse(dataFile);
        List<String[]> expected = defaultCorrectParseData;
        assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }

    @Test
    void isActionTypeNotCorrect_notOk() {
        String[] dataFile = readFileServiceTest
                .read("resources/testFiles/inputFileTestActionNotCrt.csv");
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest
                        .createTasks(parseCsvServiceTest.parse(dataFile)));
    }

    @Test
    void isNameEmpty_notOk() {
        String[] dataFile = {"b,,20"};
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest
                        .createTasks(parseCsvServiceTest.parse(dataFile)));
    }

    @Test
    void isValueEmpty_notOk() {
        String[] dataFile = {"b,banana,"};
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest
                        .createTasks(parseCsvServiceTest.parse(dataFile)));
    }

    @Test
    void isValueLessZero_notOk() {
        String[] dataFile = {"b,banana,-4"};
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest
                        .createTasks(parseCsvServiceTest.parse(dataFile)));
    }

    @Test
    void isOneLineCorrect_ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                "banana", 30);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruit);
        String[] dataFile = readFileServiceTest
                .read("resources/testFiles/inputFileTestOneCrtLine.csv");
        List<String[]> parsedData = parseCsvServiceTest.parse(dataFile);
        List<FruitTransaction> actual = new CreateTaskServiceImpl()
                .createTasks(parsedData);
        assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }

    @Test
    void isThreeLinesCorrect_ok() {
        FruitTransaction fruitOne = new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                "banana", 40);
        FruitTransaction fruitTwo = new FruitTransaction(FruitTransaction.ActionType.PURCHASE,
                "banana", 10);
        FruitTransaction fruitThree = new FruitTransaction(FruitTransaction.ActionType.SUPPLY,
                "banana", 13);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitOne);
        expected.add(fruitTwo);
        expected.add(fruitThree);
        String[] dataFile = readFileServiceTest
                .read("resources/testFiles/inputFileTestThreeCrtLines.csv");
        List<String[]> parsedData = parseCsvServiceTest.parse(dataFile);
        List<FruitTransaction> actual = new CreateTaskServiceImpl()
                .createTasks(parsedData);
        assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }

    @Test
    void isNullStorageInHandle_notOk() {
        Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTestTemp =
                new HashMap<>();
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(null));
        ActionStrategy actionStrategyTestTemp =
                new ActionStrategyImpl(actionHandlerMapTestTemp);
        ProcessStoreService handleProcessTestTemp =
                new ProcessStoreServiceImpl(actionStrategyTestTemp);
        String[] dataFile = {"b,banana,20"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTestTemp.processAction(fruitsTransactions));
    }

    @Test
    void isHandleBalance_ok() {
        String[] dataFile = {"b,banana,20"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        boolean actual = handleProcessTest.processAction(fruitsTransactions);
        assertTrue(actual);
    }

    @Test
    void isHandlePurchaseNullData_notOk() {
        Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTestTemp =
                new HashMap<>();
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(fruitDB));
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.PURCHASE,
                new PurchaseActionHandler(null));
        ActionStrategy actionStrategyTestTemp =
                new ActionStrategyImpl(actionHandlerMapTestTemp);
        ProcessStoreService handleProcessTestTemp =
                new ProcessStoreServiceImpl(actionStrategyTestTemp);
        String[] dataFile = {"b,banana,20", "p,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTestTemp.processAction(fruitsTransactions));
    }

    @Test
    void isHandlePurchaseEmptyStorage_notOk() {
        String[] dataFile = {"p,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void isHandlePurchaseNotExistProduct_notOk() {
        String[] dataFile = {"b,apple,20", "p,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void isHandlePurchase_ok() {
        String[] dataFile = {"b,apple,20", "p,apple,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        boolean actual = handleProcessTest.processAction(fruitsTransactions);
        assertTrue(actual);
    }

    @Test
    void isHandleReturnNullData_notOk() {
        Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTestTemp = new HashMap<>();
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(fruitDB));
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.PURCHASE,
                new ReturnActionHandler(null));
        ActionStrategy actionStrategyTestTemp =
                new ActionStrategyImpl(actionHandlerMapTestTemp);
        ProcessStoreService handleProcessTestTemp =
                new ProcessStoreServiceImpl(actionStrategyTestTemp);
        String[] dataFile = {"b,banana,20", "r,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTestTemp.processAction(fruitsTransactions));
    }

    @Test
    void isHandleReturnEmptyStorage_notOk() {
        String[] dataFile = {"r,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void isHandleReturnNotExistProduct_notOk() {
        String[] dataFile = {"b,apple,20", "r,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void isHandleReturn_ok() {
        String[] dataFile = {"b,apple,20", "r,apple,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        boolean actual = handleProcessTest.processAction(fruitsTransactions);
        assertTrue(actual);
    }

    @Test
    void isHandleSupplyNullData_notOk() {
        Map<FruitTransaction.ActionType, ActionHandler> actionHandlerMapTestTemp =
                new HashMap<>();
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.BALANCE,
                new BalanceActionHandler(fruitDB));
        actionHandlerMapTestTemp.put(FruitTransaction.ActionType.PURCHASE,
                new SupplyActionHandler(null));
        ActionStrategy actionStrategyTestTemp =
                new ActionStrategyImpl(actionHandlerMapTestTemp);
        ProcessStoreService handleProcessTestTemp =
                new ProcessStoreServiceImpl(actionStrategyTestTemp);
        String[] dataFile = {"b,banana,20", "s,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTestTemp.processAction(fruitsTransactions));
    }

    @Test
    void isHandleSupplyEmptyStorage_notOk() {
        String[] dataFile = {"s,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void isHandleSupplyNotExistProduct_notOk() {
        String[] dataFile = {"b,apple,20", "s,banana,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        assertThrows(ValidationDataException.class,
                () -> handleProcessTest.processAction(fruitsTransactions));
    }

    @Test
    void isHandleSupply_ok() {
        String[] dataFile = {"b,apple,20", "s,apple,10"};
        List<FruitTransaction> fruitsTransactions = createTaskServiceTest
                .createTasks(parseCsvServiceTest.parse(dataFile));
        boolean actual = handleProcessTest.processAction(fruitsTransactions);
        assertTrue(actual);
    }

    @Test
    void isReportCsvServiceNullStorage_notOk() {
        ReportCsvService reportCsvServiceTemp = new ReportCsvServiceImpl(null);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTemp.createReport());
    }

    @Test
    void isReportCsvServiceStorageNullFirst_notOk() {
        fruitDB.add(null, 20);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void isReportCsvServiceStorageEmptyFirst_notOk() {
        fruitDB.add("", 20);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void isReportCsvServiceStorageNullSecond_notOk() {
        fruitDB.add("banana", null);
        assertThrows(ValidationDataException.class,
                () -> reportCsvServiceTest.createReport());
    }

    @Test
    void isReportCsvServiceStorageOneLineCorrect_ok() {
        fruitDB.add("banana", 20);
        String[] expected = {"fruit,quantity", "\r\nbanana,20"};
        String[] actual = reportCsvServiceTest.createReport();
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void isReportCsvServiceStorageThreeLineCorrect_ok() {
        fruitDB.add("banana", 20);
        fruitDB.add("apple", 50);
        fruitDB.add("orange", 90);
        String[] expected =
                {"fruit,quantity", "\r\nbanana,20", "\r\norange,90", "\r\napple,50"};
        String[] actual = reportCsvServiceTest.createReport();
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void isWriteToFileServiceDataIsNull_notOk() {
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(null, "path"));
    }

    @Test
    void isWriteToFileServiceDataIsEmpty_notOk() {
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(new String[0], "path"));
    }

    @Test
    void isWriteToFileServicePathIsNull_notOk() {
        String[] result = {"test", "test"};
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(result, null));
    }

    @Test
    void isWriteToFileServicePathIsEmpty_notOk() {
        String[] result = {"test", "test"};
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(result, ""));
    }

    @Test
    void isWriteToFileServiceCorrect_ok() {
        String[] result = {"test", "test"};
        boolean actual = writeFileServiceTest
                .writeToFile(result,
                        "resources/testFiles/outputFileTestWriteDone.csv");
        assertTrue(actual);
    }

    @Test
    void isTestAllSystem() {
        String[] readData = readFileServiceTest
                .read("resources/testFiles/inputFileTestCorrectLines.csv");
        List<String[]> parseData = parseCsvServiceTest.parse(readData);
        List<FruitTransaction> transactionsData = createTaskServiceTest
                .createTasks(parseData);
        handleProcessTest.processAction(transactionsData);
        String[] reportData = reportCsvServiceTest.createReport();
        writeFileServiceTest.writeToFile(reportData,
                "resources/testFiles/outputFileTestCorrectLines.csv");
        List<String> resultActual = new ArrayList<>();
        String[] actual;
        try (FileReader reader = new FileReader(
                "resources/testFiles/outputFileTestCorrectLines.csv");
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                resultActual.add(readLine);
            }
            actual = resultActual.toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException("Error read file in test!");
        }
        String[] expected = {"fruit,quantity", "banana,140", "apple,80"};
        assertTrue(Arrays.deepEquals(expected, actual));
    }
}
