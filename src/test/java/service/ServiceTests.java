package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.InputDataType;
import model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.GenerateReportImpl;
import service.impl.InputDataResolverImpl;
import service.impl.ReaderServiceImpl;
import service.impl.TransactionOperationImpl;
import service.impl.WriterServiceImpl;

class ServiceTests {
    private static final String TEST_FILE = "src/main/resources/test.csv";
    private ReaderService reader;
    private WriterService writer;
    private InputDataResolver resolver;
    private TransactionOperation transactionOperation;
    private GenerateReport report;
    private FruitStorageDao fruitStorageDao;
    private Path testFilePath;

    @BeforeEach
    void setUp() {
        reader = new ReaderServiceImpl();
        writer = new WriterServiceImpl();
        resolver = new InputDataResolverImpl();
        transactionOperation = new TransactionOperationImpl();
        report = new GenerateReportImpl();
        fruitStorageDao = new FruitStorageDaoImpl();

        try {
            testFilePath = Files.createTempFile("test", ".csv");
        } catch (IOException e) {
            throw new RuntimeException("Can't create file with path " + testFilePath, e);
        }

        String testString = "operation,fruit,quantity\nb,banana,10\nb,apple,20\nb,orange,30";
        try {
            Files.write(testFilePath, testString.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + testFilePath, e);
        }
    }

    @AfterEach
    void tearDown() {
        fruitStorageDao.set("banana", 0);
        fruitStorageDao.set("apple", 0);
        try {
            Files.deleteIfExists(Path.of(TEST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file" + TEST_FILE, e);
        }
        try {
            Files.deleteIfExists(testFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + testFilePath, e);
        }
    }

    @Test
    void readFile_ok() {
        List<String> expected = new ArrayList<>(
                Arrays.asList("b,banana,10", "b,apple,20", "b,orange,30")
        );
        List<String> actual = reader.read(String.valueOf(testFilePath));
        assertEquals(expected, actual, "Actual and expected strings must be equals");
    }

    @Test
    void readWrongFilePath_notOk() {
        String fileName = "src/main/resources/wrong.csv";
        assertThrows(RuntimeException.class, () -> reader.read(fileName),
                "Can't find file with such name " + fileName);
    }

    @Test
    void dataResolverValidInput_ok() {
        List<String> input = Arrays.asList(
                "b,banana,100", "s,banana,25", "p,banana,80", "r,banana,10"
        );
        ArrayList<InputDataType> result = resolver.resolveData(input);
        int expectedElementsCount = 4;
        assertEquals(expectedElementsCount, result.size(),
                "Actual and expected sizes must be equals");

        assertEquals(Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit().getName());
        assertEquals(100, result.get(0).getFruit().getQuantity());

        assertEquals(Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("banana", result.get(1).getFruit().getName());
        assertEquals(25, result.get(1).getFruit().getQuantity());

        assertEquals(Operation.PURCHASE, result.get(2).getOperation());
        assertEquals("banana", result.get(2).getFruit().getName());
        assertEquals(80, result.get(2).getFruit().getQuantity());

        assertEquals(Operation.RETURN, result.get(3).getOperation());
        assertEquals("banana", result.get(3).getFruit().getName());
        assertEquals(10, result.get(3).getFruit().getQuantity());
    }

    @Test
    void dataResolverInvalidInput_notOk() {
        List<String> input = List.of("b,banana,", "b,apple,5", "b,orange,-8");

        assertThrows(RuntimeException.class, () -> resolver.resolveData(input),
                "Invalid input data " + input);
    }

    @Test
    void dataResolverEmptyInput_returnsEmptyList_ok() {
        List<String> input = new ArrayList<>();
        ArrayList<InputDataType> expected = new ArrayList<>();

        assertEquals(expected, resolver.resolveData(input),
                "Actual resolved data must be empty");
    }

    @Test
    void transactionOperationValidInput_ok() {
        ArrayList<InputDataType> inputData = new ArrayList<>();
        InputDataType data1 = new InputDataType(Operation.BALANCE, new Fruit("apple", 5));
        InputDataType data2 = new InputDataType(Operation.SUPPLY, new Fruit("banana", 10));
        inputData.add(data1);
        inputData.add(data2);
        transactionOperation.execute(inputData, fruitStorageDao);

        Fruit apple = fruitStorageDao.getFruit("apple");
        assertEquals(5, apple.getQuantity(),
                "Actual and expected q-ty of apples should be the same");

        Fruit banana = fruitStorageDao.getFruit("banana");
        assertEquals(10, banana.getQuantity(),
                "Actual and expected q-ty of bananas should be equals");
    }

    @Test
    void generateReport_ok() {
        fruitStorageDao.set("banana", 100);
        fruitStorageDao.set("apple", 25);
        Map<String,Integer> input = fruitStorageDao.getFruits();

        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,25");

        assertEquals(expected, report.generateReport(input),
                "Actual and expected reports must be equals");
    }

    @Test
    void writeReport_ok() {
        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,25");

        fruitStorageDao.set("banana", 100);
        fruitStorageDao.set("apple", 25);

        writer.write(expected, TEST_FILE);

        List<String> actual = new ArrayList<>();
        actual.add("fruit,quantity");
        actual.addAll(reader.read(TEST_FILE));

        assertEquals(expected, actual,
                "Actual and expected files content should be equals");
    }
}
