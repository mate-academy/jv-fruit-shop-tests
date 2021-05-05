package service;

import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import model.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;
import service.file.FileWriter;
import service.file.FileWriterImpl;

public class ReportGeneratorTest {
    public static final List<String> EXPECTED_DATA = List.of("fruit, quantity",
            "banana,10",
            "apple,20",
            "arbuz,30",
            "apelsin,15");
    public static final String CORRECT_PATH = "src\\test\\resources\\generatedReport.csv";
    private static FileWriter fileWriter;
    private static FruitDao fruitDao;
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
        fruitDao = new FruitDaoImpl();
        fruitDao.getDB().clear();
    }

    @Test
    public void generateReport_NormalData_Ok() {
        fruitDao.add(new Fruit("banana"), 10);
        fruitDao.add(new Fruit("apple"), 20);
        fruitDao.add(new Fruit("arbuz"), 30);
        fruitDao.add(new Fruit("apelsin"), 15);
        File file = new File(CORRECT_PATH);
        reportGenerator = new ReportGeneratorImpl(fileWriter, fruitDao);
        reportGenerator.generateReport(file.getPath());
        try {
            List<String> actual = Files.readAllLines(file.toPath());
            List<String> expected = EXPECTED_DATA;
            assertEquals(expected, actual);
            Files.write(file.toPath(), "".getBytes());
        } catch (IOException exception) {
            throw new RuntimeException("Can't read from file");
        }
    }
}
