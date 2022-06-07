package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static List<String> report;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
        report = new ArrayList<>();
    }

    @Before
    public void setUp() {
        report.add("fruit, quantity");
        report.add("banana, 100");
    }

    @Test
    public void writeToFile_Ok() {
        writerService.writeToFile(report, "src/test/resources/report.csv");
        List<String> expectedResult = List.of("fruit, quantity", "banana, 100");
        List<String> actualResult = readFromFile("src/test/resources/report.csv");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_directoryDoesNotExists_notOk() {
        writerService.writeToFile(report, "src/test/nonexistent/report.csv");
    }

    public List<String> readFromFile(String filePath) {
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + filePath, e);
        }
        return dataFromFile;
    }

    @After
    public void tearDown() throws Exception {
        new FileWriter("src/test/resources/report.csv").close();
    }
}
