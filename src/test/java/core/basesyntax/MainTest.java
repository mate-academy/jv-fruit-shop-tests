package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.DataConverter;
import core.basesyntax.infrastructure.DataConverterImpl;
import core.basesyntax.infrastructure.db.FileReader;
import core.basesyntax.infrastructure.db.FileReaderImpl;
import core.basesyntax.infrastructure.db.FileWriter;
import core.basesyntax.infrastructure.db.FileWriterImpl;
import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final String OPERATION_LIST_FILE_PATH
            = "src/main/resources/operationslist.csv";
    private static final String DB_FILE_PATH
            = "src/main/resources/database.csv";

    @BeforeEach
    void setUp() {

        DataConverter dataConverter = new DataConverterImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
    }

    @Test
    void readInnitDataOk() {
        String[] expectedStr = new String[] {
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        };
        List<String> expectedList = Arrays.asList(expectedStr);

        FileReader fileReader = new FileReaderImpl();
        List<String> actualList = fileReader.read(OPERATION_LIST_FILE_PATH);
        assertEquals(expectedList, actualList);
    }

    @Test
    void readInnitDataNotOk() {
        String[] expectedStr = new String[] {
                "b,banana,20",
                "b,apple,100",
                "s,banana,10",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        };
        List<String> expectedList = Arrays.asList(expectedStr);

        FileReader fileReader = new FileReaderImpl();
        List<String> actualList = fileReader.read(OPERATION_LIST_FILE_PATH);
        assertNotEquals(expectedList, actualList);

    }

    @Test
    void incorrectPathNotToReadOk() {
        FileReader fileReader = new FileReaderImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read("/invalid/path/notPath"));

        assertEquals("Can't read from file", exception.getMessage());
    }

    @Test
    void writeIntoFileOk() {
        String expectedMessage = "expected message";
        List<String> expected = new ArrayList<>();
        expected.add("expected message");
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write("expected message", DB_FILE_PATH);
        FileReader fileReader = new FileReaderImpl();
        List<String> actual = fileReader.read(DB_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void incorrectPathNotToWriteOk() {
        FileWriter fileWriter = new FileWriterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write("", "/invalid/path/notPath"));

        assertEquals("Can't open the file: /invalid/path/notPath", exception.getMessage());
    }

    @Test
    void dataConvertOk() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

        FileReader reader = new FileReaderImpl();
        List<String> read = reader.read(OPERATION_LIST_FILE_PATH);
        DataConverter converter = new DataConverterImpl();
        List<FruitTransaction> actual = converter.convertToTransaction(read);

        assertEquals(expected, actual);
    }

    @Test
    void dataStoredOk() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5));

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 105);
        assertEquals(Storage.STORAGE, expected);
    }

    @Test
    void applicationWorkResultOk() {
        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");

        Main.main(new String[0]);
        FileReader reader = new FileReaderImpl();
        List<String> actual = reader.read(DB_FILE_PATH);

        assertEquals(actual, expected);
    }

    @Test
    void dataConverterTestStringOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,wrong");
        DataConverter dataConverter = new DataConverterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));

        assertEquals("Invalid number format: 'wrong'. Expected an integer value.",
                exception.getMessage());
    }

    @Test
    void dataConverterTestLessZeroOk() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,-10");
        DataConverter dataConverter = new DataConverterImpl();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));

        assertEquals("Error! Number can't be less than zero", exception.getMessage());
    }

    @Test
    void notFoundProduct() {
        Storage.STORAGE.remove("banana");
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));

        assertEquals("Can't find fruit: banana", exception.getMessage());

    }

    @Test
    void notEnoughProduct() {
        Storage.STORAGE.put("banana", 10);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = new ArrayList<>();

        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 9999));

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));

        assertEquals("Too little of product: banana", exception.getMessage());

    }

    @Test
    void testGetOperationValidCode() {
        // Test with a valid code
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.getOperation("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.getOperation("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.getOperation("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.getOperation("r"));
    }

    @Test
    void testGetOperationInvalidCode() {
        // Test with an invalid code to ensure IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.getOperation("invalid"));
    }
}
