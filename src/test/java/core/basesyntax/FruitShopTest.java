package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FileReaderCsvImpl;
import core.basesyntax.dao.FileWriterImpl;
import core.basesyntax.dao.FruitFileReader;
import core.basesyntax.dao.FruitFileWriter;
import core.basesyntax.db.ShopDataBase;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operationhandler.BalanceOperation;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperation;
import core.basesyntax.service.operationhandler.ReturnOperation;
import core.basesyntax.service.operationhandler.SupplyOperation;
import core.basesyntax.service.parser.ParseFruitData;
import core.basesyntax.service.parser.ParseFruitDataImpl;
import core.basesyntax.service.reportgenerator.ReportGenerator;
import core.basesyntax.service.reportgenerator.ReportGeneratorImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopTest {
    private static final String INPUT_FILE = "src/test/java/resources/TestInput.csv";
    private static final String OUTPUT_FILE = "src/test/java/resources/TestOutput.csv";
    private FruitFileReader fileReader;
    private FruitFileWriter fileWriter;
    private ParseFruitData parser;
    private ReportGenerator generator;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;
    private Map<FruitTransaction.Operation, OperationHandler> allHandlers;

    @BeforeEach
    void beforeAll() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderCsvImpl();
        parser = new ParseFruitDataImpl();
        generator = new ReportGeneratorImpl();
        balanceHandler = new BalanceOperation();
        supplyHandler = new SupplyOperation();
        purchaseHandler = new PurchaseOperation();
        returnHandler = new ReturnOperation();
        allHandlers = new HashMap<>();
        allHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        allHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        allHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        allHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);
    }

    @Test
    void testFileReaderNotCsv_NotOk() {
        assertThrows(IllegalArgumentException.class,() -> fileReader.read("Test.txt"));
    }

    @Test
    void testFileReaderThrowsException_NotOk() {
        assertThrows(RuntimeException.class,() -> fileReader.read(""));
    }

    @Test
    void testFileReader_Ok() throws IOException {
        Path inputFile = Path.of(INPUT_FILE);

        Files.write(inputFile, List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,apple,50",
                "p,apple,30",
                "r,apple,10",
                "b,banana,20",
                "p,banana,5"
        ));

        List<String> fileInputImpl = fileReader.read(INPUT_FILE);
        List<String> fileInputFiles = Files.readAllLines(inputFile);
        assertEquals(fileInputImpl, fileInputFiles);
    }

    @Test
    void testFileWriterThrowsException_NotOk() {
        assertThrows(RuntimeException.class,() -> fileWriter.write(List.of(),""));
    }

    @Test
    void testFileWriter_Ok() throws IOException {
        Path outPut = Path.of(OUTPUT_FILE);
        List<String> inputList = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90"

        );
        Files.write(outPut, inputList);

        List<String> fileOutPutFiles = Files.readAllLines(outPut);

        fileWriter.write(inputList, OUTPUT_FILE);

        List<String> fileOutPutImpl = Files.readAllLines(outPut);
        assertEquals(fileOutPutImpl, fileOutPutFiles);
    }

    @Test
    void testQuantityLessThanZero_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction("r","apple",-45));
    }

    @Test
    void testInvalidOperationCode_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void testParseValidData_Ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> result = parser.parseData(input);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void testParseEmptyData_Ok() {
        List<String> input = List.of("type,fruit,quantity");

        List<FruitTransaction> result = parser.parseData(input);

        assertTrue(result.isEmpty());
    }

    @Test
    void testParseInvalidFormat_NotOk() {
        List<String> input = List.of("type1,type2,fruit,quantity",
                "r,b,apple,200");
        assertThrows(IllegalArgumentException.class,() -> parser.parseData(input));
    }

    @Test
    void testParseEmptyData_NotOk() {
        List<String> input = List.of();
        assertThrows(IllegalArgumentException.class,() -> parser.parseData(input));
    }

    @Test
    void testParseInvalidOperation_NotOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,20"
        );

        assertThrows(IllegalArgumentException.class, () -> parser.parseData(input));
    }

    @Test
    void testParseInvalidQuantity_NotOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,abc"
        );

        assertThrows(IllegalArgumentException.class, () -> parser.parseData(input));
    }

    @Test
    void testBalanceSetsInitialQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction("b", "apple", 100);
        balanceHandler.process(transaction);
        assertEquals(100, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testBalanceOverwritesExistingQuantity_Ok() {
        ShopDataBase.shopData.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction("b", "banana", 75);
        balanceHandler.process(transaction);
        assertEquals(75, ShopDataBase.shopData.get("banana"));
    }

    @Test
    void testSupplyAddsToExistingQuantity_Ok() {
        ShopDataBase.shopData.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("s", "apple", 50);
        supplyHandler.process(transaction);
        assertEquals(150, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testSupplyCreatesNewEntry_Ok() {
        FruitTransaction transaction = new FruitTransaction("s", "pear", 30);
        supplyHandler.process(transaction);

        assertEquals(30, ShopDataBase.shopData.get("pear"));
    }

    @Test
    void testPurchaseReducesQuantity_Ok() {
        ShopDataBase.shopData.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction("p", "banana", 30);
        purchaseHandler.process(transaction);
        assertEquals(70, ShopDataBase.shopData.get("banana"));
    }

    @Test
    void testPurchaseFailsWhenNotEnoughStock_NotOk() {
        ShopDataBase.shopData.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction("p", "apple", 20);
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.process(transaction));
    }

    @Test
    void testPurchaseNonExistentFruit_NotOk() {
        FruitTransaction transaction = new FruitTransaction("p", "peach", 5);
        assertThrows(IllegalArgumentException.class, () ->
                purchaseHandler.process(transaction));
    }

    @Test
    void testReturnAddsToExistingQuantity_Ok() {
        ShopDataBase.shopData.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction("r", "apple", 10);
        returnHandler.process(transaction);
        assertEquals(60, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testReturnNonExistingFruit_NotOk() {
        FruitTransaction transaction = new FruitTransaction("r", "mango", 15);
        assertThrows(IllegalArgumentException.class,() -> returnHandler.process(transaction));
    }

    @Test
    void testGenerateReport_Ok() {
        ShopDataBase.shopData.put("apple", 100);
        ShopDataBase.shopData.put("banana", 50);

        List<String> report = generator.generateReport();

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("apple,100"));
        assertTrue(report.contains("banana,50"));
    }

    @Test
    void testGenerateEmptyReport_Ok() {
        List<String> report = generator.generateReport();
        assertEquals(1, report.size());
        assertEquals("fruit,quantity", report.get(0));
    }

    @Test
    void testStrategyWithAllOperationHandlers_Ok() {
        OperationStrategy fullStrategy = new OperationStrategyImpl(allHandlers);
        assertInstanceOf(BalanceOperation.class,
                fullStrategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(SupplyOperation.class,
                fullStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertInstanceOf(PurchaseOperation.class,
                fullStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(ReturnOperation.class,
                fullStrategy.getHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void testGetHandlerForNullOperation_NotOk() {
        OperationStrategy strategy = new OperationStrategyImpl(allHandlers);
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.getHandler(null);
        });
    }

    @Test
    void testCompleteWorkflow_Ok() throws IOException {
        Path inputFile = Path.of(INPUT_FILE);

        Files.write(inputFile, List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,apple,50",
                "p,apple,30",
                "r,apple,10",
                "b,banana,20",
                "p,banana,5"
        ));

        List<String> fileInput = fileReader.read(INPUT_FILE);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> parsedData = parser.parseData(fileInput);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(parsedData);

        List<String> report = generator.generateReport();
        assertTrue(report.contains("apple,130"));
        assertTrue(report.contains("banana,15"));

        fileWriter.write(report, OUTPUT_FILE);

        assertEquals(130, ShopDataBase.shopData.get("apple"));
        assertEquals(15, ShopDataBase.shopData.get("banana"));

        Path outputFile = Path.of(OUTPUT_FILE);
        List<String> output = Files.readAllLines(outputFile);
        assertTrue(output.contains("apple,130"));
        assertTrue(output.contains("banana,15"));

    }

    @AfterEach
    void tearDown() throws IOException {
        ShopDataBase.shopData.clear();
    }

}
