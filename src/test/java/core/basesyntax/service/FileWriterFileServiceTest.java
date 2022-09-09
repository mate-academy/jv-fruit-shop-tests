package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterFileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterFileServiceTest {
    private static final String RESULT_FILE = "src/test/java/resources/result.csv";
    private static final String NOT_EXIST_FILE = "";
    private static FileWriterService fileWriter;
    private final String report = "DEFAULT REPORT";

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterFileServiceImpl();
    }

    @Test
    public void write_validDate_ok() {
        fileWriter.write(RESULT_FILE, report);
        List<String> expected = readFromFile(RESULT_FILE);
        List<String> actual = List.of(report);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_notExistFile_NotOk() {
        fileWriter.write(NOT_EXIST_FILE, report);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file ", e);
        }
    }
}
