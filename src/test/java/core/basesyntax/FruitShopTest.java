package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.serviceimpl.DataConverterImpl;
import core.basesyntax.serviceimpl.FileReaderImpl;
import core.basesyntax.serviceimpl.FileWriterImpl;
import core.basesyntax.serviceimpl.OperationStrategyImpl;
import core.basesyntax.serviceimpl.ReportGeneratorImpl;
import core.basesyntax.serviceimpl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopTest {
    private FileReader fileReader;
    private FileWriter fileWriter;
    private DataConverter dataConverter;
    private ShopService shopService;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
        fileWriter = new FileWriterImpl();
        dataConverter = new DataConverterImpl();
        reportGenerator = new ReportGeneratorImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        shopService = new ShopServiceImpl(operationStrategy);

        Storage.fruits.clear();
    }

    @Test
    void read_validFile_correct() {
        List<String> data = fileReader.read("src/test/java/core/basesyntax/testInput");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertEquals("b,banana,20", data.get(1));
    }

    @Test
    void write_validFile_correct() throws Exception {
        final String testPath = "src/test/java/core/basesyntax/testOutput";
        Storage.add("pineapple", 40);
        Storage.add("apple", 30);
        String resultingReport = reportGenerator.getReport();

        fileWriter.write(resultingReport, testPath);

        Path path = Paths.get(testPath);
        String actual = Files.readString(path).trim();

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "pineapple,40";
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_validData_success() {
        List<String> rawData = List.of("type,fruit,quantity", "b,banana,20", "p,apple,30");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(rawData);
        assertEquals(2, transactions.size());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals(20, transactions.get(0).getQuantity());
    }

    @Test
    void applyOperations_correctlyUpdatesStorage() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)
        );
        shopService.process(transactions);
        assertEquals(130, Storage.getFruitQuantity("banana"));
    }

    @Test
    void unknownOperation_throwsException() {
        RuntimeException thrown = assertThrows(
                RuntimeException.class, () -> FruitTransaction.Operation.fromCode("q"));
        assertEquals("No enum constant for code: q", thrown.getMessage());
    }

    @Test
    void purchaseMoreThanAvailable_throwsException() {
        Storage.add("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 30);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> new PurchaseOperation().apply(fruitTransaction));
        assertEquals("Balance is negative", thrown.getMessage());
    }

    @Test
    void reportGenerator_generationCorrect() {
        Storage.add("banana", 20);
        Storage.add("apple", 30);
        assertEquals(reportGenerator.getReport(),
                "fruit,quantity" + System.lineSeparator()
                        + "banana,20" + System.lineSeparator()
                        + "apple,30" + System.lineSeparator());
    }

    @Test
    void transactionProcess_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, "banana", 100)
        );
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> shopService.process(transactions));
        assertEquals("No handler for operation: null", thrown.getMessage());
    }

    @Test
    void write_invalidFile_throwsException() {
        String invalidFilePath = "src/main/test/resources/";
        RuntimeException thrown = assertThrows(
                RuntimeException.class, () -> fileWriter.write("banana, 20", invalidFilePath));
        assertTrue(thrown.getMessage().contains("Can't write file to path: " + invalidFilePath));
    }

    @Test
    void read_invalidFile_throwsException() {
        String invalidFilePath = "src/main/test/resources/";
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> fileReader.read(invalidFilePath));
        assertEquals("Can't read file from path: "
                + invalidFilePath, thrown.getMessage());
    }

    @Test
    void getFruitQuantity_fruitNameIsNull_throwsException() {
        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> Storage.getFruitQuantity(null));
        assertEquals("Fruit can't be null", thrown.getMessage());
    }

    @Test
    void getFruitQuantity_wrongFruitName_throwsException() {
        Storage.add("apple", 20);
        assertEquals(0, Storage.getFruitQuantity("banana")); ;
    }

    @Test
    void decreaseQuantity_FruitReachesZero_RemovesFromStorage() {
        String fruit = "banana";
        int quantity = 5;
        Storage.add(fruit, quantity);

        Storage.remove(fruit, quantity);

        assertEquals(0, Storage.getFruitQuantity(fruit));
        assertFalse(Storage.fruits.containsKey(fruit));
    }
}
