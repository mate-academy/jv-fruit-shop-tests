package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String TEST_FILE = "testFile.csv";
    private static final String TEST_REPORT = "test";
    private static FileWriterService fileWriterService;
    private static Path testPath;

    @BeforeClass
    public static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
        testPath = Path.of(TEST_FILE);
    }

    @Test
    public void writeToFile_existFile_Ok() {
        try {
            Files.createFile(testPath);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to test file", e);
        }
        fileWriterService.writeToFile(TEST_FILE, TEST_REPORT);
        String actual = readFromFile(TEST_FILE);
        assertEquals(TEST_REPORT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistFile_notOk() {
        String report = "wrong_report";
        fileWriterService.writeToFile(TEST_FILE, report);
    }

    private String readFromFile(String path) {
        Path pathToFile = Path.of(path);
        try {
            return Files.readString(pathToFile);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }

    @After
    public void afterEach() {
        try {
            Files.deleteIfExists(testPath);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete test file", e);
        }
    }
}
