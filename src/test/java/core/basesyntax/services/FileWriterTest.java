package core.basesyntax.services;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String DATA = "Hello,world\n1,2\n3,4";
    private static final String PATH = "src/test/resources/";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeReportToFile_data_Ok() {
        Path fileWithResults = fileWriterService.writeReportToFile(DATA, PATH);
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
        Path path = fileWriterService.writeReportToFile("", PATH);
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
}
