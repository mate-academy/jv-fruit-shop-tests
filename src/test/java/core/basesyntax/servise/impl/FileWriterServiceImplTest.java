package core.basesyntax.servise.impl;

import core.basesyntax.servise.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_FILEPATH = "src/resources/daily_report.csv";
    private static final String INVALID_FILEPATH = "src/resources/test/test.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void init() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validFilePath_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                        + "banana,100" + System.lineSeparator()
                        + "apple,100";
        fileWriterService.writeStringToFile(VALID_FILEPATH, report);
        List<String> actual = readFile(VALID_FILEPATH);
        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,100");

        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        fileWriterService.writeStringToFile(INVALID_FILEPATH, "report");
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullFilePath_notOk() {
        fileWriterService.writeStringToFile(null, "report");
    }

    private List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("File not accessible " + filePath, e);
        }
    }

    @AfterClass
    public static void deleteCreatedFile() {
        try {
            Files.delete(Path.of(VALID_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + VALID_FILEPATH,e);
        }
    }
}
