package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionExecutorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JvFruitShopTest {
    private final FruitsDao fruitsDao = new FruitsDaoImpl();
    private static List<FruitTransaction> fruits;

    @BeforeAll
    static void beforeAll() {
        FruitTransaction fruitTransaction1 = new FruitTransaction("b", "banana", 100);
        FruitTransaction fruitTransaction2 = new FruitTransaction("p", "banana", 20);
        FruitTransaction fruitTransaction3 = new FruitTransaction("b", "apple", 200);
        FruitTransaction fruitTransaction4 = new FruitTransaction("b", "orange", 50);
        FruitTransaction fruitTransaction5 = new FruitTransaction("p", "orange", 10);
        FruitTransaction fruitTransaction6 = new FruitTransaction("p", "apple", 25);
        FruitTransaction fruitTransaction7 = new FruitTransaction("s", "orange", 10);
        FruitTransaction fruitTransaction8 = new FruitTransaction("s", "apple", 10);
        FruitTransaction fruitTransaction9 = new FruitTransaction("s", "kiwi", 150);
        FruitTransaction fruitTransaction10 = new FruitTransaction("r", "apple", 15);
        fruits = new ArrayList<>();
        fruits.add(fruitTransaction1);
        fruits.add(fruitTransaction2);
        fruits.add(fruitTransaction3);
        fruits.add(fruitTransaction4);
        fruits.add(fruitTransaction5);
        fruits.add(fruitTransaction6);
        fruits.add(fruitTransaction7);
        fruits.add(fruitTransaction8);
        fruits.add(fruitTransaction9);
        fruits.add(fruitTransaction10);
    }

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void read_File_isOk() {
        File inputFile = new File("src/test/resources/inputDataTest.csv");
        FileReader reader = new FileReaderImpl();
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        List<String> actual = reader.readFile(inputFile);
        assertEquals(expected, actual);
    }

    @Test
    void read_File_NotOk() {
        File inputFile = new File("");
        FileReader fileReader = new FileReaderImpl();
        assertThrows(RuntimeException.class, () -> fileReader.readFile(inputFile));
    }

    @Test
    void write_File_isOk() {
        FileReader fileReader = new FileReaderImpl();
        FileWriter fileWriter = new FileWriterImpl();
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,154");
        expected.add("apple,80");
        expected.add("kiwi,50");
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,154" + System.lineSeparator()
                + "apple,80" + System.lineSeparator()
                + "kiwi,50" + System.lineSeparator();
        fileWriter.writeToFile(report);
        File result = new File("src/main/resources/result.csv");
        List<String> actual = fileReader.readFile(result);
        assertEquals(expected, actual);
    }

    @Test
    void execute_Transaction_isOk() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 80);
        expected.put("apple", 200);
        expected.put("orange", 50);
        expected.put("kiwi", 150);
        TransactionExecutor transactionExecutor = new TransactionExecutorImpl();
        Map<String, Integer> actual = transactionExecutor.executeAll(fruits);
        assertEquals(expected, actual);
    }

    @Test
    void generate_Report_isOk() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,50" + System.lineSeparator()
                + "apple,200" + System.lineSeparator()
                + "kiwi,150" + System.lineSeparator();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        fruitsDao.storageAccess().put("banana", 100);
        fruitsDao.storageAccess().put("apple", 200);
        fruitsDao.storageAccess().put("orange", 50);
        fruitsDao.storageAccess().put("kiwi", 150);
        String actual = reportGenerator.generateReport(fruitsDao.storageAccess());
        assertEquals(expected, actual);
    }

    @Test
    void parse_Transaction_isOk() {
        List<String> fruitTransaction = new ArrayList<>();
        fruitTransaction.add("type,fruit,quantity");
        fruitTransaction.add("b,banana,100");
        fruitTransaction.add("p,banana,20");
        fruitTransaction.add("b,apple,200");
        fruitTransaction.add("b,orange,50");
        fruitTransaction.add("p,orange,10");
        fruitTransaction.add("p,apple,25");
        fruitTransaction.add("s,orange,10");
        fruitTransaction.add("s,apple,10");
        fruitTransaction.add("s,kiwi,150");
        fruitTransaction.add("r,apple,15");
        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> actual = transactionParser.parse(fruitTransaction);
        assertEquals(fruits, actual);
    }
}
