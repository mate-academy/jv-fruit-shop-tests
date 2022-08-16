package core.basesyntax.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterTest {
    private static final String VALID_PATH_TO_FILE = "src/test/resources/validDailyRestTotals.csv";
    private static final String INVALID_PATH_TO_FILE =
            "src/test/resources_/invalidDailyRestTotals.csv";
    private static CsvFileWriter csvFileWriter;
    private static String activities;

    @BeforeClass
    public static void beforeClass() {
        csvFileWriter = new CsvFileWriter();
        activities = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
    }

    @Test
    public void writeFile_validPath_Ok() {
        String expectedValue = activities;
        csvFileWriter.writeFile(VALID_PATH_TO_FILE, activities);
        String actualValue = readFile(VALID_PATH_TO_FILE);
        Assert.assertEquals(actualValue, expectedValue);
    }

    @Test(expected = RuntimeException.class)
    public void fileWtiter_invalidPath_Ok() {
        csvFileWriter.writeFile(INVALID_PATH_TO_FILE, activities);
    }

    @Test(expected = RuntimeException.class)
    public void fileWtiter_nullPath_Ok() {
        csvFileWriter.writeFile(null, activities);
    }

    private String readFile(String path) {
        File file = new File(path);
        try {
            List<String> list = Files.readAllLines(file.toPath());
            return list.stream()
                    .map(s -> s + System.lineSeparator())
                    .collect(Collectors.joining())
                    .trim();
        } catch (IOException e) {
            throw new RuntimeException("Could not read data from file " + path, e);
        }
    }
}
