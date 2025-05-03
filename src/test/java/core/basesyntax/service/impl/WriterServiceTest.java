package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        List<String> report = new ArrayList<>();
        report.add("fruit, quantity");
        report.add("banana, 100");
        writerService.writeToFile(report, "src/test/resources/report.csv");
        List<String> expectedResult = List.of("fruit, quantity", "banana, 100");
        List<String> actualResult = readFromFile("src/test/resources/report.csv");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_directoryDoesNotExists_notOk() {
        writerService.writeToFile(Collections.emptyList(), "src/test/nonexistent/report.csv");
    }

    private List<String> readFromFile(String filePath) {
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + filePath, e);
        }
        return dataFromFile;
    }
}
