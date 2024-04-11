package core.basesyntax;

import core.basesyntax.database.DataBase;
import core.basesyntax.database.Operation;
import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import core.basesyntax.impl.FileServiceImpl;
import core.basesyntax.impl.FruitshopServiceImpl;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.impl.TransactionServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileService;
import core.basesyntax.service.FruitshopService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final String PATH1 = "./src/test/resources/testData";
    private static final String PATH2 = "./src/test/resources/testData2";
    private static final String PATH3 = "./src/test/resources/testData3";
    private static final String DATA_FILE_PATH = "./src/main/resources/beginningData";
    private static final String REPORT_FILE_PATH = "./src/main/resources/report";
    private static final String INVALID_PATH = "./src/main/user/data/content/file";
    private static final Map<Operation, OperationHandler> handlerMap = new HashMap<>();
    private static final int INDEX_OF_FIRST_FRUIT = 0;
    private static final int INDEX_OF_SECOND_FRUIT = 1;
    private final FileService fileService = new FileServiceImpl();
    private final ReportService reportService = new ReportServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final FruitshopService fruitshopService = new FruitshopServiceImpl(handlerMap);

    @Test
    void parseDataOk() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,100");
        expected.add("b,apple,100");
        List<String> actual = fileService.readDataFromFile(PATH3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertDataOk() {
        List<String> strings = fileService.readDataFromFile(PATH1);
        List<FruitTransaction> actual = transactionService.parseData(strings);
        List<FruitTransaction> expected = new ArrayList<>();
        FruitTransaction fruit1 = new FruitTransaction(Operation.BALANCE, "banana", 100);
        FruitTransaction fruit2 = new FruitTransaction(Operation.BALANCE, "apple", 100);
        expected.add(fruit1);
        expected.add(fruit2);
        Assertions.assertEquals(actual.get(INDEX_OF_FIRST_FRUIT).getFruit(), fruit1.getFruit());
        Assertions.assertEquals(actual.get(INDEX_OF_SECOND_FRUIT).getFruit(), fruit2.getFruit());

    }

    @Test
    void processDataThrowsOk() {
        List<FruitTransaction> nullFruits = new ArrayList<>();
        nullFruits.add(new FruitTransaction(null, "banana", 100));
        nullFruits.add(new FruitTransaction(null, "apple", 100));
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitshopService.processData(nullFruits));
    }

    @Test
    void processDataOk() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        Assertions.assertEquals(DataBase.mapDb.get("banana"), 90);
        Assertions.assertEquals(DataBase.mapDb.get("apple"), 150);
    }

    @Test
    void generateReportOk() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String actual = reportService.createReport();
        String expected = "banana,90" + System.lineSeparator()
                + "apple,150" + System.lineSeparator();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void writeToFileOk() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(DATA_FILE_PATH);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String report = reportService.createReport();
        String expected = "banana,90" + System.lineSeparator()
                + "apple,150" + System.lineSeparator();
        fileService.write(REPORT_FILE_PATH, report);
        List<String> checkReport = fileService.readDataFromFile(REPORT_FILE_PATH);
        StringBuilder builder = new StringBuilder();
        for (String value : checkReport) {
            builder.append(value);
            builder.append(System.lineSeparator());
        }
        Assertions.assertEquals(expected, builder.toString());
    }

    @Test
    void checkResultOneOk() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(PATH1);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String report = reportService.createReport();
        String expected = "banana,10" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        Assertions.assertEquals(expected, report);
    }

    @Test
    void checkResultTwoOk() {
        initializeMap();
        List<String> strings = fileService.readDataFromFile(PATH2);
        List<FruitTransaction> fruitTransactions = transactionService.parseData(strings);
        fruitshopService.processData(fruitTransactions);
        String report = reportService.createReport();
        String expected = "banana,15" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        Assertions.assertEquals(expected, report);
    }

    @Test
    void fileServiceThrowsOk() {
        String content = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.readDataFromFile(INVALID_PATH));
        Assertions.assertThrows(RuntimeException.class,
                () -> fileService.write(INVALID_PATH, content));
    }

    private static void initializeMap() {
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }
}
