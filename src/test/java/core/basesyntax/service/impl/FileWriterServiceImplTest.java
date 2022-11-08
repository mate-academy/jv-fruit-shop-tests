package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_PATH = "src/main/resources/ReportFile.csv";
    private static final String DATA_TO_WRITE = "Some text";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_isOk() {
        fileWriterService.writeToFile(DATA_TO_WRITE, REPORT_PATH);
        String actual = readFile();
        assertEquals(DATA_TO_WRITE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeInFile_nullData_NotOk() {
        fileWriterService.writeToFile(null, REPORT_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_NotOk() {
        fileWriterService.writeToFile(DATA_TO_WRITE, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        fileWriterService.writeToFile(DATA_TO_WRITE, null);
    }

    private String readFile() {
        try {
            return Files.readString(Path.of(FileWriterServiceImplTest.REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: "
                    + FileWriterServiceImplTest.REPORT_PATH, e);
        }
    }
}
