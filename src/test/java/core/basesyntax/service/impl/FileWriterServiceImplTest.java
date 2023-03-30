package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String DATA = "Hello,world\n1,2\n3,4";
    private static final String VALID_PATH = "src/test/resources/test2";
    private static final String INVALID_PATH = "J:/MATE/test/resources/";

    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeReportToFile_data_Ok() {
        Path fileWithResults = fileWriterService.writeReportToFile(DATA, VALID_PATH);
        List<String> expected = List.of("Hello,world","1,2","3,4");
        List<String> actual;
        try {
            actual = Files.readAllLines(fileWithResults);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeReportToFile_empty_Ok() {
        Path path = fileWriterService.writeReportToFile("", VALID_PATH);
        List<String> expected = List.of();
        List<String> actual;
        try {
            actual = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToFile_nullValues_notOk() {
        fileWriterService.writeReportToFile(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToFile_invalidPath_notOk() {
        fileWriterService.writeReportToFile(DATA, INVALID_PATH);
    }
}
