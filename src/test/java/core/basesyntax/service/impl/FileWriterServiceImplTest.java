package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.WrongPathException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/report.csv";
    private static final String INVALID_FILE_PATH = "not.today";
    private static final String DATA_FOR_TEST_FILE = "test sentence";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void init() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_writeWithValidPath_ok() {
        fileWriterService.writeToFile(VALID_FILE_PATH, DATA_FOR_TEST_FILE);
        String actual;
        try {
            actual = Files.readString(Path.of(VALID_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Can`t get data from file %s", VALID_FILE_PATH));
        }
        assertEquals(String.format("Should write \"%s\" to file, but was -> %s",
                DATA_FOR_TEST_FILE, actual), DATA_FOR_TEST_FILE, actual);
    }

    @Test (expected = WrongPathException.class)
    public void writeToFile_writeWithInvalidPath_notOk() {
        fileWriterService.writeToFile(INVALID_FILE_PATH, DATA_FOR_TEST_FILE);
    }

    @Test (expected = WrongPathException.class)
    public void writeToFile_pathIsNull_notOk() {
        fileWriterService.writeToFile(null, DATA_FOR_TEST_FILE);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_writeDataIsNull_notOk() {
        fileWriterService.writeToFile(VALID_FILE_PATH, null);
    }
}
