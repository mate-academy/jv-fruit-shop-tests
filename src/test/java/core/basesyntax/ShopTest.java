package core.basesyntax;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import dao.FruitDao;
import dao.FruitDaoImpl;
import db.Storage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.FruitTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileReaderService;
import service.FileWriterService;
import service.OperationHandler;
import service.OperationStrategy;
import service.ParseService;
import service.ShopService;
import service.impl.CsvFileReaderServiceImpl;
import service.impl.CsvParseServiceImpl;
import service.impl.FileWriterServiceImpl;
import service.impl.ShopServiceImpl;
import strategy.AddOperationHandler;
import strategy.OperationStrategyImpl;
import strategy.SetBalanceOperationHandler;
import strategy.SubtractOperationHandler;

public class ShopTest {
    private static FruitDao fruitDao;
    private static FileReaderService fileReadService;
    private static ParseService parseService;
    private static OperationStrategy operationStrategy;
    private static FileWriterService fileWriterService;
    private static ShopService shopService;
    private static final String[][] STENCIL = {{"JavaCore", "JavaSOLID", "JavaFruitShopTests"},
            {"test", "read-writeCsvFile", "2022"}};
    private static final String FILE_PATH_FROM = "src/main/java/resources/readingTest.csv";
    private static final String FILE_PATH_TO = "src/main/java/resources/writingTest.csv";
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        fruitDao = new FruitDaoImpl();
        fileReadService = new CsvFileReaderServiceImpl();
        fileWriterService = new FileWriterServiceImpl();
        parseService = new CsvParseServiceImpl();
        shopService = new ShopServiceImpl(fruitDao);
        Map<FruitTransaction.Operation, OperationHandler> mapOperation = new HashMap<>();
        mapOperation.put(FruitTransaction.Operation.BALANCE,
                new SetBalanceOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.PURCHASE,
                new SubtractOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.RETURN,
                new AddOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.SUPPLY,
                new AddOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(mapOperation);
        fileWriterService.writeFile(FILE_PATH_FROM,
                Arrays.stream(STENCIL).collect(Collectors.toUnmodifiableList()));
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void readFile_readFromCsvFile_ok() {
        List<String[]> list = fileReadService.readFile(FILE_PATH_FROM);
        assertEquals("strings from first row of the csv file and obtained"
                + " result aren't the same", STENCIL[0], list.get(0));
        assertEquals("strings from second row of the csv file and obtained"
                + " result aren't the same", STENCIL[1], list.get(1));
        assertEquals("expected row's quantity and quantity of rows from"
                + " stencil aren't the same", STENCIL.length, list.size());
    }

    @Test
    public void readFile_throwsNullPointerExceptionIfPathIsNull_ok() {
        exception.expect(NullPointerException.class);
        fileReadService.readFile(null);
    }

    @Test
    public void readFile_throwsRuntimeExceptionIfPathIsNotCorrect_ok() {
        String filePathError = "mistaken/path/file.csv";
        exception.expect(RuntimeException.class);
        exception.expectMessage("Cannot read file");
        fileReadService.readFile(filePathError);
    }

    @Test
    public void parse_parseCorrectFetchedData_ok() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"s", "apple", "35"});
        list.add(new String[]{"b", "apple", "50"});
        FruitTransaction transaction1 = new FruitTransaction("s", "apple", 35);
        FruitTransaction transaction2 = new FruitTransaction("b", "apple", 50);
        assertEquals("method parse didn't return expected quantity of objects",
                2, parseService.parse(list).size());
        assertEquals("method parse didn't return correspond result",
                 transaction1, parseService.parse(list).get(0));
        assertEquals("method parse didn't return correspond result",
                transaction2, parseService.parse(list).get(1));
    }

    @Test
    public void parse_throwsRuntimeExceptionIfFetchedDataFormatWasIncorrect_notOk() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"33", "cows"});
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input data doesn't correspond require format");
        parseService.parse(list);
    }

    @Test
    public void parse_throwsRuntimeExceptionIfFetchedOperationWasIncorrect_notOk() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"k","apricots", "100"});
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input data doesn't correspond require format");
        parseService.parse(list);
    }

    @Test
    public void parse_throwsRuntimeExceptionWithBlankArrays_notOk() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{});
        exception.expect(RuntimeException.class);
        exception.expectMessage("Input data doesn't correspond require format");
        parseService.parse(list);
    }

    @Test
    public void getOperationHandler_obtainCorrespondingOperation_ok() {
        final String message = "method getOperationHandler returned incorrect handler";
        final OperationHandler operationForSupply = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);
        final OperationHandler operationForReturn = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN);
        final OperationHandler operationForPurchase = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE);
        final OperationHandler operationForSetBalance = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(message, operationForSupply.getClass(), AddOperationHandler.class);
        assertEquals(message, operationForReturn.getClass(), AddOperationHandler.class);
        assertEquals(message, operationForPurchase.getClass(), SubtractOperationHandler.class);
        assertEquals(message, operationForSetBalance.getClass(), SetBalanceOperationHandler.class);
    }

    @Test
    public void getOperationHandler_getSomeHandlerIfOperationNull_notOk() {
        final String message = "method getOperationHandler shouldn't return any operationHandler"
                + " if input data is NULL. Result of method should be NULL also\n";
        assertNull(message, operationStrategy.getOperationHandler(null));
    }

    @Test
    public void doReport_getReportFromDB_ok() {
        final String message1 = "there had to be head message in the report but it wasn't";
        final String message2 = "failed output data.\n";
        Storage.fruits.clear();
        fruitDao.add("apples", 100);
        fruitDao.add("oranges", 100);
        fruitDao.add("nuts", 100);
        List<String[]> reportStrings = shopService.doReport();
        assertEquals(message1, reportStrings.get(0), new String[]{"fruit", "quantity"});
        assertArrayEquals(message2, reportStrings.get(1), new String[]{"oranges", "100"});
        assertArrayEquals(message2, reportStrings.get(2), new String[]{"nuts", "100"});
        assertArrayEquals(message2, reportStrings.get(3), new String[]{"apples", "100"});
    }

    @Test
    public void writeFile_allDataHasBeenWritten_ok() {
        String message = "original data and data from file are differed";
        fileWriterService.writeFile(FILE_PATH_TO, Arrays.asList(STENCIL));
        assertArrayEquals(message, fileReadService.readFile(FILE_PATH_TO).get(0), STENCIL[0]);
        assertArrayEquals(message, fileReadService.readFile(FILE_PATH_TO).get(1), STENCIL[1]);
    }

    @Test
    public void doTransaction_SupplyOrReturnOperationsWorkRight_ok() {
        String message = "expected quantity and actual are different";
        OperationHandler operationHandler = new AddOperationHandler(fruitDao);
        operationHandler.doTransaction(new FruitTransaction("s", "watermelon", 10));
        operationHandler.doTransaction(new FruitTransaction("r", "watermelon", 5));
        assertEquals(message, 15, fruitDao.get("watermelon").intValue());
    }

    @Test
    public void doTransaction_purchaseOperationQuantityFruitsEnough_ok() {
        String message = "expected quantity and actual are different";
        OperationHandler addOperationHandler = new AddOperationHandler(fruitDao);
        OperationHandler subtractOperationHandler = new SubtractOperationHandler(fruitDao);
        addOperationHandler.doTransaction(new FruitTransaction("s", "banana", 50));
        subtractOperationHandler.doTransaction(new FruitTransaction("p", "banana", 10));
        assertEquals(message, 40, fruitDao.get("banana").intValue());
    }

    @Test
    public void doTransaction_purchaseOperationQuantityFruitsNotEnough_notOk() {
        OperationHandler subtractOperationHandler = new SubtractOperationHandler(fruitDao);
        exception.expect(RuntimeException.class);
        exception.expectMessage("you have no fruit's quantity available for PURCHASE operation");
        subtractOperationHandler.doTransaction(new FruitTransaction("p", "potato", 10));
    }

    @Test
    public void doTransaction_balanceOperationWhenSomeQuantityIsPresent_ok() {
        String message = "something wrong";
        OperationHandler supplyOperationHandler = new AddOperationHandler(fruitDao);
        OperationHandler balanceOperationHandler = new SetBalanceOperationHandler(fruitDao);
        supplyOperationHandler.doTransaction(new FruitTransaction("s", "fungi", 10));
        balanceOperationHandler.doTransaction(new FruitTransaction("s", "fungi", 50));
        assertEquals(message, 50, fruitDao.get("fungi").intValue());
    }

    @AfterClass
    public static void tearDown() {
        File destroyReadingFile = new File("src/main/java/resources/readingTest.csv");
        File destroyWritingFile = new File("src/main/java/resources/writingTest.csv");
        destroyReadingFile.delete();
        destroyWritingFile.delete();
    }
}
