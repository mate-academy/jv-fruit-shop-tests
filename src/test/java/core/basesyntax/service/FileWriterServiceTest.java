package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static FileWriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        String path = "src/test/resources/testReport";
        String report = "fruit, quantity";
        String expected = "fruit, quantity";
        writerService.write(report, path);
        String actual = read(path);
        assertEquals(expected, actual);
    }

    private String read(String path) {
        try {
            return Files.readString(Path.of(path)).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test (expected = NullPointerException.class)
    public void nullPath_WriteToFile_NotOk() {
        String nullTest = null;
        writerService.write(null, nullTest);
    }

    @Test (expected = NullPointerException.class)
    public void nullReport_WriteToFile_NotOk() {
        String path = "src/main/resources/report.csv";
        writerService.write(path, null);
    }

    @Test (expected = NullPointerException.class)
    public void nullValues_WriteToFile_NotOk() {
        writerService.write(null, null);
    }
}
