package core.basesyntax;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.TransactionParserImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JvFruitShopTest {
    private final File inputFile = new File("src/test/resources/inputDataTest.csv");
    private FruitsDao fruitsDao = new FruitsDaoImpl();

    @AfterEach
    void tearDown() {
        fruitsDao.storageAccess().clear();
    }

    @Test
    void read_File_isOk() {
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
    void write_File_isOk() {
        FileReader fileReader = new FileReaderImpl();
        FileWriter fileWriter = new FileWriterImpl();
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,154");
        expected.add("apple,80");
        expected.add("kivy,50");
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,154" + System.lineSeparator()
                + "apple,80" + System.lineSeparator()
                + "kivy,50" + System.lineSeparator();
        fileWriter.writeToFile(report);
        File result = new File("src/main/resources/result.csv");
        List<String> actual = fileReader.readFile(result);
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
        FruitTransaction fruitTransaction1 = new FruitTransaction("b", "banana", 100);
        FruitTransaction fruitTransaction2 = new FruitTransaction("p", "banana", 20);
        FruitTransaction fruitTransaction3 = new FruitTransaction("b", "apple", 200);
        FruitTransaction fruitTransaction4 = new FruitTransaction("b", "orange", 50);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransaction1);
        expected.add(fruitTransaction2);
        expected.add(fruitTransaction3);
        expected.add(fruitTransaction4);

        List<String> fruits = new ArrayList<>();
        fruits.add("type,fruit,quantity");
        fruits.add("b,banana,100");
        fruits.add("p,banana,20");
        fruits.add("b,apple,200");
        fruits.add("b,orange,50");

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> actual = transactionParser.parse(fruits);
        assertEquals(expected, actual);
    }
}
