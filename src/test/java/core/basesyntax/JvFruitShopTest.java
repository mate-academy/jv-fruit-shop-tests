package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JvFruitShopTest {
    private static List<FruitTransaction> expected;
    private final TransactionExecutor transactionExecutor = new TransactionExecutorImpl();
    private final FruitsDao fruitsDao = new FruitsDaoImpl();
    private final FileReader fileReader = new FileReaderImpl();
    private final FileWriter fileWriter = new FileWriterImpl();
    private final TransactionParser transactionParser = new TransactionParserImpl();

    @BeforeAll
    static void beforeAll() {
        expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", "banana", 100));
        expected.add(new FruitTransaction("p", "banana", 20));
        expected.add(new FruitTransaction("b", "apple", 200));
        expected.add(new FruitTransaction("b", "orange", 50));
        expected.add(new FruitTransaction("p", "orange", 10));
        expected.add(new FruitTransaction("p", "apple", 25));
        expected.add(new FruitTransaction("s", "orange", 10));
        expected.add(new FruitTransaction("s", "apple", 10));
        expected.add(new FruitTransaction("s", "kiwi", 150));
        expected.add(new FruitTransaction("r", "apple", 15));
    }

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void read_File_isOk() {
        File inputFile = new File("src/test/resources/isOkReadFile.csv");
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        List<String> actual = fileReader.readFile(inputFile);
        assertEquals(expected, actual);
    }

    @Test
    void read_File_NotOk() {
        File inputFile = new File("");
        assertThrows(RuntimeException.class, () -> fileReader.readFile(inputFile));
    }

    @Test
    void write_File_isOk() {
        File file = new File("src/test/resources/isOkWriteFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        Map<String, Integer> fruitsDataStorage = transactionExecutor.executeAll(fruits);
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String report = reportGenerator.generateReport(fruitsDataStorage);
        fileWriter.writeToFile(report);
        File result = new File("src/main/resources/result.csv");
        List<String> fruitsList = fileReader.readFile(result);
        StringBuilder builder = new StringBuilder();
        for (String fruit: fruitsList) {
            builder.append(fruit).append(System.lineSeparator());
        }
        String actual = builder.toString();
        assertEquals(report, actual);
    }

    @Test
    void write_File_NotOk() {
        String report = null;
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(report));
    }

    @Test
    void execute_Transaction_isOk() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 110);
        expected.put("orange", 50);
        expected.put("kiwi", 25);
        expected.put("test", 100);
        File file = new File("src/test/resources/isOkExecuteTransactionFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        Map<String, Integer> actual = transactionExecutor.executeAll(fruits);
        assertEquals(expected, actual);
    }

    @Test
    void return_Fruit_NotOk() {
        File file = new File("src/test/resources/notOkReturnTransactionFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }

    @Test
    void return_Quantity_NotOk() {
        File file = new File("src/test/resources/notOkReturnQuantityFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }

    @Test
    void supply_Quantity_NotOk() {
        File file = new File("src/test/resources/notOkSupplyQuantityFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }

    @Test
    void purchase_Quantity_NotOk() {
        File file = new File("src/test/resources/notOkPurchaseQuantityFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }

    @Test
    void transaction_Unknown_NotOk() {
        File file = new File("src/test/resources/notOkUnknownTransactionFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }

    @Test
    void balance_Quantity_NotOk() {
        File file = new File("src/test/resources/notOkBalanceQuantityFile.csv");
        List<String> fruitsTransaction = fileReader.readFile(file);
        List<FruitTransaction> fruits = transactionParser.parse(fruitsTransaction);
        assertThrows(RuntimeException.class, () -> transactionExecutor.executeAll(fruits));
    }

    @Test
    void generate_Report_isOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        fruitsDao.storageAccess().put("banana", 100);
        fruitsDao.storageAccess().put("apple", 200);
        fruitsDao.storageAccess().put("orange", 50);
        fruitsDao.storageAccess().put("kiwi", 150);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,50" + System.lineSeparator()
                + "apple,200" + System.lineSeparator()
                + "kiwi,150" + System.lineSeparator();
        String actual = reportGenerator.generateReport(fruitsDao.storageAccess());
        assertEquals(expected, actual);
    }

    @Test
    void parse_Transaction_isOk() {
        File file = new File("src/test/resources/isOkParseTransactionFile.csv");
        List<String> fruitTransaction = fileReader.readFile(file);
        List<FruitTransaction> actual = transactionParser.parse(fruitTransaction);
        assertEquals(expected, actual);
    }
}
