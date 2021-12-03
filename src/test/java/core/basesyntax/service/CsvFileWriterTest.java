package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriterTest {
    private static CsvFileWriter writer;
    private static Fruit banana;
    private static Fruit apple;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        writer = new CsvFileWriterImpl();
        banana = new Fruit();
        apple = new Fruit();
    }

    @Before
    public void setUp() {
        banana.setFruitName("banana");
        banana.setFruitQuantity(10);
        FruitStorage.fruits.add(banana);
        apple.setFruitName("apple");
        apple.setFruitQuantity(20);
        FruitStorage.fruits.add(apple);
    }

    @Test
    public void invalidFilePath_NotOk() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can't write data to file");
        writer.createReportFile("src/testresources/reportFile.csv");
    }

    @Test
    public void create_ReportFile() {
        String expected = "fruit,quantity\nbanana,10\napple,20";
        writer.createReportFile("src/test/resources/reportFile.csv");
        String actual = dataFromReportFile();
        assertEquals(expected, actual);
    }

    @After
    public void afterTest() {
        FruitStorage.fruits.clear();
    }

    private String dataFromReportFile() {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/reportFile.csv"))) {
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can not read file", e);
        }
        return builder.toString().trim();
    }
}
