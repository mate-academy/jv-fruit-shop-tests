package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_ACTUAL_PATH = "src/test/java/resources/reportActual.csv";
    private static final String REPORT_EXPECTED_PATH = "src/test/java/resources/reportExpected.csv";
    private static final String DATA_TO_WRITE = "testing writeFile_Ok method";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeFile_validData_ok() {
        List<String> expected = readFile(REPORT_EXPECTED_PATH);
        fileWriterService.writeFile(DATA_TO_WRITE, REPORT_ACTUAL_PATH);
        List<String> actual = readFile(REPORT_ACTUAL_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_emptyData_notOk() {
        fileWriterService.writeFile("", REPORT_ACTUAL_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_pathIsInvalid_notOk() {
        fileWriterService.writeFile(DATA_TO_WRITE, "src/test");
    }

    private List<String> readFile(String path) {
        List<String> fileData;
        try {
            fileData = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + path, e);
        }
        return fileData;
    }
}
